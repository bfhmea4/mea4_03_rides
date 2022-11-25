package com.spring.webtest.request;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.dto.RideRequestDto;
import com.spring.webtest.service.RideRequestService;

class RideRequestServiceInvoker implements RideRequestInvoker {

    private final RideRequestService service;

    RideRequestServiceInvoker(RideRequestService service) {
        this.service = service;
    }

    @Override
    public RideRequestDto createRequest(RideRequest rideRequest) throws OperationNotSupportedException, IllegalAccessException {
        return service.addRideRequest(rideRequest);
    }

    @Override
    public RideRequestDto getRequest(long id) {
        return service.findRideRequestById(id);
    }

    @Override
    public RideRequestDto updateRequest(RideRequest rideRequest) throws IllegalAccessException {
        return service.updateRideRequest(rideRequest);
    }

    @Override
    public void deleteRequest(long id) throws IllegalAccessException {
        service.deleteRideRequest(id);

    }
}