package com.spring.webtest.service;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDto> getAll() {
        List<UserDto> userList = new ArrayList<>();
        repository.findAll().forEach(user -> {
            userList.add(userToDto(user));
        });
        return userList;
    }

    public UserDto getById(Long id) {
        return userToDto(repository.findById(id).orElse(null));
    }

    public UserDto save(User user) {
        return userToDto(repository.save(user));
    }

    public UserDto update(User user) {
        return userToDto(repository.save(user));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    private UserDto userToDto(User user) {
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