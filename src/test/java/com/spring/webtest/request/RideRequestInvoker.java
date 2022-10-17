package com.spring.webtest.request;

import com.spring.webtest.database.entities.RideRequest;

interface RideRequestInvoker {
    RideRequest createRequest(String text);
    RideRequest getRequest(long id);
    RideRequest updateRequest(long id, String text);
    void deleteRequest(long id);
}