package com.spring.webtest.exception;

public class RideOfferNotFoundException extends ResourceNotFoundException {
    public RideOfferNotFoundException(long id) {
        super(String.format("Could not find ride offer with ID %s", id));
    }

    public RideOfferNotFoundException(String message) {
        super(message);
    }
}

