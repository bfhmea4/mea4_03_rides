package com.spring.webtest.service;

import com.spring.webtest.exception.UnauthenticatedException;
import com.spring.webtest.security.AuthContext;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.TokenDto;
import com.spring.webtest.exception.UserNotFoundException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public final UserRepository repository;
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
        authContext.assureIsAuthenticated();
        User user = repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id)
        );
        authContext.assureHasUsername(user.getEmail());
        return user;
    }

    public User getByEmail(String email) {
        authContext.assureIsAuthenticated();
        User user = repository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(String.format("Could not find User with email '%s'", email))
        );
        authContext.assureHasUsername(user.getEmail());
        return user;
    }

    public TokenDto save(User user) throws JoseException {
        user.setPassword(hashService.hash(user.getPassword()));
        return new TokenDto(authService.generateJwt(repository.save(user)));
    }

    public User update(User user) {
        authContext.assureHasUsername(user.getEmail());
        if (user.getPassword() != null) {
            user.setPassword(hashService.hash(user.getPassword()));
        }
        return repository.save(user);
    }

    public void delete(long id) {
        authContext.assureIsAuthenticated();
        User user = repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id)
        );
        authContext.assureHasUsername(user.getEmail());
        repository.deleteById(id);
    }

    public TokenDto loginUser(LoginDto loginDto) throws IllegalAccessException, JoseException {
        User user = repository.findByEmail(loginDto.getEmail()).orElseThrow(() ->
            new UnauthenticatedException("Credentials are not valid")
        );
        if (authService.credentialsAreValid(loginDto, user)) {
            logger.info("credentials of user " + loginDto.getEmail() + " are valid");
            return new TokenDto(this.authService.generateJwt(user));
        }
        throw new UnauthenticatedException("Credentials are not valid");
    }
}
