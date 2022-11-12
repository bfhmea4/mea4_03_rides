package com.spring.webtest.request;

import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.dto.RideRequestDto;

interface RideRequestInvoker {
    RideRequestDto createRequest(RideRequest rideRequest);
    RideRequestDto getRequest(long id);
    RideRequestDto updateRequest(RideRequest rideRequest);
    void deleteRequest(long id);
}