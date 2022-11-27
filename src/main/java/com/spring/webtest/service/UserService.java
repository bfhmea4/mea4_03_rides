package com.spring.webtest.service;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.ResourceNotFoundException;
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
