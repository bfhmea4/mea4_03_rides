package com.spring.webtest.service;

import com.spring.webtest.security.AuthContext;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.TokenDto;
import com.spring.webtest.exception.UserNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository repository;
    private final HashService hashService;
    private final AuthService authService;

    private final AuthContext authContext;

    @Autowired
    public UserService(UserRepository repository, HashService hashService, AuthService authService, AuthContext authContext) {
        this.repository = repository;
        this.hashService = hashService;
        this.authService = authService;
        this.authContext = authContext;
    }

    public User getById(Long id) {
        authContext.assureHasId(id);
        return repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id)
        );
    }

    public User getByEmail(String email) {
        User user = repository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(String.format("Could not find User with email '%s'", email))
        );
        //authContext.assureHasId(user.getId());
        return user;
    }

    public User getByToken(String token) throws IllegalAccessException, MalformedClaimException {
        Long id = this.authService.getIdFromToken(token);
        return getById(id);
    }

    public TokenDto save(User user) throws JoseException {
        System.out.println("User password: " + user.getPassword());
        user.setPassword(hashService.hash(user.getPassword()));
        return new TokenDto(authService.generateJwt(repository.save(user)));
    }

    public User update(User user) throws IllegalAccessException {
        authContext.assureHasId(user.getId());
        if (user.getPassword() != null) {
            user.setPassword(hashService.hash(user.getPassword()));
        }
        return repository.save(user);
    }

    public void delete(long id) throws IllegalAccessException {
        authContext.assureHasId(id);
        repository.deleteById(id);
    }

    public TokenDto loginUser(LoginDto loginDto) throws IllegalAccessException, JoseException {
        User user = this.getByEmail(loginDto.getEmail());
        if (authService.credentialsAreValid(loginDto, user)) {
            logger.info("credentials of user " + loginDto.getEmail() + " are valid");
            return new TokenDto(this.authService.generateJwt(user));
        }
        throw new IllegalAccessException("Credentials are not valid");
    }
}
