package com.spring.webtest.user;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;

public class UserServiceInvoker implements UserInvoker {

    UserService service;

    public UserServiceInvoker(UserService service) {
        this.service = service;
    }

    @Override
    public UserDto getUser(long id) {
        return service.getById(id);
    }

    @Override
    public UserDto createUser(User user) {
        return service.save(user);
    }

    @Override
    public UserDto updateUser(User user) {
        return service.update(user);
    }

    @Override
    public void deleteUser(long id) {
        service.delete(id);
    }
}
