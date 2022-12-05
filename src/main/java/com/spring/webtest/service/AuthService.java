package com.spring.webtest.service;

import com.spring.webtest.controller.UserController;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.LoginDto;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthService {

    //            Tutorial from https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());
    RsaJsonWebKey rsaJsonWebKey;
    HashService hashService;

    public AuthService(HashService hashService) throws JoseException {
        rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        rsaJsonWebKey.setKeyId("k1");
        this.hashService = hashService;
    }

    public boolean credentialsAreValid(LoginDto loginDto, User user) {
        loginDto.setPassword(hashService.hash(loginDto.getPassword()));
        return loginDto.getPassword().equals(user.getPassword());
    }

    public Long getIdFromToken(String token) throws MalformedClaimException, IllegalAccessException {
        JwtClaims claims = getClaimsFromToken(token);
        if (claims != null) {
            return (Long) claims.getClaimValue("userId");
        }
        throw new IllegalAccessException("Could not get Info out of the Token");
    }

    public boolean tokenIsValid(String jwt) throws MalformedClaimException {
        return getClaimsFromToken(jwt) != null;
    }

    public String generateJwt(User user) throws JoseException {

        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Service Provider");
        claims.setAudience("Audience");
        claims.setExpirationTimeMinutesInTheFuture(20);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(2);
        claims.setSubject("AuthToken");
        claims.setClaim("userId", user.getId());
        claims.setClaim("email", user.getEmail());

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jwt = jws.getCompactSerialization();
        logger.info("Successfully created token");
        return jwt;
    }

    public JwtClaims getClaimsFromToken(String jwt) throws MalformedClaimException {
        // Remove the substring "Bearer"
        jwt = jwt.substring(7);

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("Service Provider")
                .setExpectedAudience("Audience")
                .setVerificationKey(rsaJsonWebKey.getPublicKey())
                .setJweAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                        AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();

        try {
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            System.out.println("JWT validation succeeded! " + jwtClaims);
            return jwtClaims;
        } catch (InvalidJwtException e) {
//                Failed Processing or validating
            System.out.println("Invalid JWT! " + e);

            // Programmatic access to (some) specific reasons for JWT invalidity is also possible
            // should you want different error handling behavior for certain conditions.

            // Whether or not the JWT has expired being one common reason for invalidity
            if (e.hasExpired())
            {
                System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
                return null;
            }

            // Or maybe the audience was invalid
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID))
            {
                System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
                return null;
            }
        }
        return null;
    }

}
