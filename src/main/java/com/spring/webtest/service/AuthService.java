package com.spring.webtest.service;

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

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthService {

    //Tutorial from https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());
    private final RsaJsonWebKey rsaJsonWebKey;
    private final HashService hashService;
    private final JwtConsumer jwtConsumer;

    public AuthService(HashService hashService) throws JoseException {
        this.hashService = hashService;

        rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        rsaJsonWebKey.setKeyId("k1");

        jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("Service Provider")
                .setExpectedAudience("Audience")
                .setVerificationKey(rsaJsonWebKey.getPublicKey())
                .setJweAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                        AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();
    }

    public boolean credentialsAreValid(LoginDto loginDto, User user) {
        loginDto.setPassword(hashService.hash(loginDto.getPassword()));
        return loginDto.getPassword().equals(user.getPassword());
    }

    public Long getIdFromToken(String token) throws IllegalAccessException {
        JwtClaims claims = getClaimsFromToken(token);
        if (claims != null) {
            return (long) claims.getClaimValue("userId");
        }
        throw new IllegalAccessException("Could not get Info out of the Token");
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

    public JwtClaims getClaimsFromToken(String jwt) {
        try {
            return jwtConsumer.processToClaims(jwt);
        } catch (InvalidJwtException e) {
            logger.log(Level.INFO, "JWT Token is invalid: {0}", e.getMessage());

            if (e.hasErrorCode(ErrorCodes.EXPIRED))
            {
                try {
                    logger.log(Level.INFO, "JWT expired at {0}", e.getJwtContext().getJwtClaims().getExpirationTime());
                } catch (MalformedClaimException malformedClaimException) {
                    logger.log(Level.WARNING, "Could not get Expiration Time from Token", malformedClaimException);
                }
            }

            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID))
            {
                try {
                    logger.log(Level.INFO, "JWT has wrong audience: {0}", e.getJwtContext().getJwtClaims().getAudience());
                } catch (MalformedClaimException malformedClaimException) {
                    logger.log(Level.WARNING, "Could not get Audience from Token", malformedClaimException);
                }
            }
        }
        return null;
    }

}
