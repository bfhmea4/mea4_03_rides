package com.spring.webtest.exception;

public class RideRequestNotFoundException extends ResourceNotFoundException {
    public RideRequestNotFoundException(long id) {
        super(String.format("Could not find ride request with ID %s", id));
    }

    public RideRequestNotFoundException(String message) {
        super(message);
    }
}

