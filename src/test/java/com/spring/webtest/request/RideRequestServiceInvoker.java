package com.spring.webtest.request;

import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.service.RideRequestService;

class RideRequestServiceInvoker implements RideRequestInvoker {

    private final RideRequestService service;

    RideRequestServiceInvoker(RideRequestService service) {
        this.service = service;
    }

    @Override
    public RideRequest createRequest(String content) {
        RideRequest rideRequest = new RideRequest(content);
        return service.addRideRequest(rideRequest);
    }

    @Override
    public RideRequest getRequest(long id) {
        return service.findRideRequestById(id);
    }

    @Override
    public RideRequest updateRequest(long id, String content) {
        RideRequest rideRequest = new RideRequest(id, content);
        return service.updateRideRequest(rideRequest);
    }

    @Override
    public void deleteRequest(long id) {
        service.deleteRideRequestById(id);

    }
}