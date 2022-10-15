package com.spring.webtest.service;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import com.spring.webtest.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDto> getAll() {
        List<UserDto> userList = new ArrayList<UserDto>();
        Iterable<User> iter = repository.findAll();
        iter.forEach(user -> {
            userList.add(userToDto(user));
        });
        return userList;
    }

    public UserDto getById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.map(this::userToDto).orElse(null);
    }

    public UserDto save(User user) {

        User u = repository.save(user);
        return userToDto(u);
    }

    private UserDto userToDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress());
    }

}
