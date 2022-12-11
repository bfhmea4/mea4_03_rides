package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.RideRequestRepository;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideRequestService {
    private final RideRequestRepository repository;
    private final UserService userService;

    private final AddressService addressService;



    @Autowired
    public RideRequestService(RideRequestRepository repository, UserService userService, AddressService addressService) {
        this.repository = repository;
        this.userService = userService;
        this.addressService = addressService;
    }

    public RideRequest addRideRequest(RideRequest rideRequest) throws IllegalAccessException, OperationNotSupportedException {
        try {
            User user = userService.getById(rideRequest.getUser().getId());
            if (rideRequest.getUser().equals(user)) {
                AddressDto fromAddress = addressService.addAddress(rideRequest.getFromAddress());
                AddressDto toAddress = addressService.addAddress(rideRequest.getToAddress());
                rideRequest.getFromAddress().setId(fromAddress.getId());
                rideRequest.getToAddress().setId(toAddress.getId());
                return repository.save(rideRequest);
            }
            throw new IllegalAccessException();
        } catch (NullPointerException e) {
            throw new OperationNotSupportedException();
        }
    }

    public List<RideRequest> findAllRideRequests() {
        List<RideRequest> requestsList = new ArrayList<>();
        repository.findAll().forEach(requestsList::add);
        return requestsList;
    }

    public RideRequest findRideRequestById(long id) {
        return repository.findById(id).orElse(null);
    }

    public RideRequest updateRideRequest(RideRequest rideRequest) throws IllegalAccessException {
        RideRequest saved = repository.findById(rideRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + rideRequest.getId() + " not found"));
        if (rideRequest.getUser() == null || saved.getUser().getId() != rideRequest.getUser().getId()) {
            throw new IllegalAccessException();
        }
        addressService.updateAddress(rideRequest.getFromAddress());
        addressService.updateAddress(rideRequest.getToAddress());
        return repository.save(rideRequest);
    }

    public void deleteRideRequest(long id) throws IllegalAccessException {
        RideRequest saved = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + id + " not found"));

        //TODO Check id of user logged in and saved to ensure deletion only from owner of request

//        if (rideRequest.getUser() == null || saved.getUser().getId() != rideRequest.getUser().getId()) {
//            throw new IllegalAccessException();
//        }
        repository.deleteById(saved.getId());
    }
}