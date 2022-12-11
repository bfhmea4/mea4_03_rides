package com.spring.webtest.service;

import com.spring.webtest.context.UserContext;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.TokenDto;
import com.spring.webtest.exception.UserNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository repository;
    private final HashService hashService;
    private final AuthService authService;

    @Autowired
    UserContext userContext;

    @Autowired
    public UserService(UserRepository repository, HashService hashService, AuthService authService) {
        this.repository = repository;
        this.hashService = hashService;
        this.authService = authService;
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        repository.findAll().forEach(userList::add);
        return userList;
    }

    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id)
        );
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(String.format("Could not find User with email '%s'", email))
        );
    }

    public User getByToken(String token) throws MalformedClaimException, IllegalAccessException {
        Long id = this.authService.getIdFromToken(token);
        return getById(id);
    }

    public TokenDto save(User user) throws JoseException {
        System.out.println("User password: " + user.getPassword());
        user.setPassword(hashService.hash(user.getPassword()));
        return new TokenDto(authService.generateJwt(repository.save(user)));
    }

    public User update(User user) throws MalformedClaimException, IllegalAccessException {
        User loggedInUser = this.userContext.getUser();
        if (user.getId() == loggedInUser.getId()) {
            if (user.getPassword() != null) {
                user.setPassword(hashService.hash(user.getPassword()));
            }
            user.setPassword(loggedInUser.getPassword());
            return repository.save(user);
        }
        throw new IllegalAccessException("Token is not valid");
    }

    public void delete(long id) throws IllegalAccessException, MalformedClaimException {
        User loggedInUser = this.userContext.getUser();
        if (id == loggedInUser.getId()) {
            repository.deleteById(id);
            return;
        }
        throw new IllegalAccessException("Token is not valid");
    }

    public TokenDto loginUser(LoginDto loginDto) throws JoseException, IllegalAccessException {
        User user = this.getByEmail(loginDto.getEmail());
        if (authService.credentialsAreValid(loginDto, user)) {
            logger.info("credentials of user " + loginDto.getEmail() + " are valid");
            return new TokenDto(this.authService.generateJwt(user));
        }
        throw new IllegalAccessException("Credentials are not valid");
    }
}
