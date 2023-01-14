package com.spring.webtest.user;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserServiceInvoker implements UserInvoker {

    @Autowired
    private UserService service;

    @Override
    public UserDto getUser(long id) {
        return null;
    }

    @Override
    public UserDto createUser(User user) {
        return null;
    }

    @Override
    public UserDto updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(long id) {

    }
}
