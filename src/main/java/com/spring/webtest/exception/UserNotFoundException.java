package com.spring.webtest.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(long id) {
        super(String.format("Could not find user with ID %s", id));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
