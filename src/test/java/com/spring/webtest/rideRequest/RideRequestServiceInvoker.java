//package com.spring.webtest.rideRequest;
//
//import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
//import com.spring.webtest.database.entities.RideRequest;
//import com.spring.webtest.dto.RideRequestDto;
//import com.spring.webtest.service.RideRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public
//class RideRequestServiceInvoker implements RideRequestInvoker {
//
//    @Autowired
//    private RideRequestService service;
//
//    @Override
//    public RideRequestDto createRequest(RideRequest rideRequest) throws OperationNotSupportedException, IllegalAccessException {
//        return service.addRideRequest(rideRequest);
//    }
//
//    @Override
//    public RideRequestDto getRequest(long id) {
//        return service.findRideRequestById(id);
//    }
//
//    @Override
//    public RideRequestDto updateRequest(RideRequest rideRequest) throws IllegalAccessException {
//        return service.updateRideRequest(rideRequest);
//    }
//
//    @Override
//    public void deleteRequest(long id) throws IllegalAccessException {
//        service.deleteRideRequest(id);
//
//    }
//}