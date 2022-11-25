package com.spring.webtest.service;

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

public class AuthService {

    //            Tutorial from https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples

    RsaJsonWebKey rsaJsonWebKey;

    public String generateJwt(LoginDto loginDto) throws JoseException {

        rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        rsaJsonWebKey.setKeyId("k1");

        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Service Provider");
        claims.setAudience(loginDto.getEmail());
        claims.setExpirationTimeMinutesInTheFuture(20);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(2);
        claims.setSubject(loginDto.getEmail());
        claims.setClaim("email", loginDto.getEmail());

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jwt = jws.getCompactSerialization();
        System.out.println("JWT: " + jwt);

        return jwt;
    }

    public boolean jwtIsValid(String jwt) throws JoseException, MalformedClaimException {

        rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        rsaJsonWebKey.setKeyId("k1");

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("Service Provider")
                .setExpectedAudience("email")
                .setVerificationKey(rsaJsonWebKey.getKey())
                .setJweAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                        AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();

        try {
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            System.out.println("JWT validation succeeded! " + jwtClaims);
            return true;
        } catch (InvalidJwtException e) {
//                Failed Processing or validating
            System.out.println("Invalid JWT! " + e);

            // Programmatic access to (some) specific reasons for JWT invalidity is also possible
            // should you want different error handling behavior for certain conditions.

            // Whether or not the JWT has expired being one common reason for invalidity
            if (e.hasExpired())
            {
                System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
                return false;
            }

            // Or maybe the audience was invalid
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID))
            {
                System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
                return false;
            }
        }
        return false;
    }

}
