package com.spring.webtest.service;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.UserDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final HashService hashService;

    @Autowired
    public UserService(UserRepository repository, HashService hashService) {
        this.repository = repository;
        this.hashService = hashService;
    }

    public List<UserDto> getAll() {
        List<UserDto> userList = new ArrayList<>();
        repository.findAll().forEach(user -> userList.add(userToDto(user)));
        return userList;
    }

    public UserDto getById(Long id) {
        return userToDto(repository.findById(id).orElse(null));
    }

    public UserDto getByEmail(String email) {
        return userToDto(repository.findByEmail(email));
    }

    public UserDto save(User user) {
        user.setPassword(hashService.hash(user.getPassword()));
        return userToDto(repository.save(user));
    }

    public UserDto update(User user) {
        user.setPassword(hashService.hash(user.getPassword()));
        return userToDto(repository.save(user));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public UserDto compareCredentials(LoginDto loginDto) throws JoseException, MalformedClaimException {
        loginDto.setPassword(hashService.hash(loginDto.getPassword()));
        User user = repository.findByEmail(loginDto.getEmail());
        if (user != null && user.getPassword().equals(loginDto.getPassword())){

//            Tutorial from https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
            RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
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

//            Decrypt the JWT:
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setAllowedClockSkewInSeconds(30)
                    .setRequireSubject()
                    .setExpectedIssuer("Service Provider")
                    .setExpectedAudience(loginDto.getEmail())
                    .setVerificationKey(rsaJsonWebKey.getKey())
                    .setJweAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                            AlgorithmIdentifiers.RSA_USING_SHA256)
                    .build();

            try {
                JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
                System.out.println("JWT validation succeeded! " + jwtClaims);
                return userToDto(user);
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
        }
        return null;
    }

    public UserDto userToDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress());
    }

}
