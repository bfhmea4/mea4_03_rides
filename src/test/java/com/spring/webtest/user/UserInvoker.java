package com.spring.webtest.user;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;

public interface UserInvoker {
    UserDto getUser(long id);

    UserDto createUser(User user);

    UserDto updateUser(User user);

    void deleteUser(long id);
}
