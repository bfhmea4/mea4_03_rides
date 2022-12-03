package com.spring.webtest.service;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.TokenDto;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final HashService hashService;
    private final AuthService authService;

    @Autowired
    public UserService(UserRepository repository, HashService hashService, AuthService authService) {
        this.repository = repository;
        this.hashService = hashService;
        this.authService = authService;
    }

    public List<UserDto> getAll() {
        List<UserDto> userList = new ArrayList<>();
        repository.findAll().forEach(user -> userList.add(userToDto(user)));
        return userList;
    }

    public UserDto getById(Long id) {
        return userToDto(repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Could not find user with id: " + id)));
    }

    public User getByEmail(String email) {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Could not find user with email: " + email);
        }
        return user;
    }

    public UserDto getByToken(String token) throws MalformedClaimException, IllegalAccessException {
        Long id = this.authService.getIdFromToken(token);
        return getById(id);
    }

    public TokenDto save(User user) throws JoseException {
        user.setPassword(hashService.hash(user.getPassword()));
        return new TokenDto(authService.generateJwt(repository.save(user)));
    }

    public UserDto update(User user, String token) throws MalformedClaimException, IllegalAccessException {
        if (authService.tokenIsValid(token) && user.getId() == getByToken(token).getId()) {
            if (user.getPassword() != null) {
                user.setPassword(hashService.hash(user.getPassword()));
            }
            user.setPassword(repository.findById(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find user with id: " + user.getId()))
                    .getPassword());
            return userToDto(repository.save(user));
        }
        throw new IllegalAccessException("Token is not valid");
    }

    public void delete(long id, String token) throws IllegalAccessException, MalformedClaimException {
        if (authService.tokenIsValid(token) && id == getByToken(token).getId()) {
            repository.deleteById(id);
            return;
        }
        throw new IllegalAccessException("Token is not valid");
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

    public TokenDto loginUser(LoginDto loginDto) throws JoseException, IllegalAccessException {
        User user = this.getByEmail(loginDto.getEmail());
        if (authService.credentialsAreValid(loginDto, user)) {
            return new TokenDto(this.authService.generateJwt(user));
        }
        throw new IllegalAccessException("Credentials are not valid");
    }
}
