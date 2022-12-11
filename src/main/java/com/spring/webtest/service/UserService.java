package com.spring.webtest.service;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.exception.UserNotFoundException;
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

    public User save(User user) {
        user.setPassword(hashService.hash(user.getPassword()));
        return repository.save(user);
    }

    public User update(User user) {
        user.setPassword(hashService.hash(user.getPassword()));
        return repository.save(user);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public User compareCredentials(String email, String password) {
        User user = getByEmail(email);
        String hashedPassword = hashService.hash(password);
        if (user.getPassword().equals(hashedPassword)){
            return user;
        }
        return null;
    }
}
