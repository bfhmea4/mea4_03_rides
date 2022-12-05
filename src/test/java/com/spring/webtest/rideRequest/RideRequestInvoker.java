package com.spring.webtest.rideRequest;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.dto.RideRequestDto;

public interface RideRequestInvoker {
    RideRequestDto createRequest(RideRequest rideRequest) throws OperationNotSupportedException, IllegalAccessException;
    RideRequestDto getRequest(long id);
    RideRequestDto updateRequest(RideRequest rideRequest) throws IllegalAccessException;
    void deleteRequest(long id) throws IllegalAccessException;
}