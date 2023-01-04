package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.exception.RideRequestNotFoundException;
import com.spring.webtest.security.AuthContext;
import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.repositories.RideRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideRequestService {
    private final RideRequestRepository repository;
    private final AddressService addressService;

    @Autowired
    private AuthContext authContext;

    @Autowired
    public RideRequestService(RideRequestRepository repository, AddressService addressService) {
        this.repository = repository;
        this.addressService = addressService;
    }

    public RideRequest addRideRequest(RideRequest rideRequest) throws IllegalAccessException, OperationNotSupportedException {
        authContext.assureIsAuthenticated();
        try {
            Address fromAddress = addressService.addAddress(rideRequest.getFromAddress());
            Address toAddress = addressService.addAddress(rideRequest.getToAddress());
            rideRequest.getFromAddress().setId(fromAddress.getId());
            rideRequest.getToAddress().setId(toAddress.getId());
            return repository.save(rideRequest);
        } catch (NullPointerException e) {
            throw new OperationNotSupportedException();
        }
    }

    public List<RideRequest> findAllRideRequests() {
        authContext.assureIsAuthenticated();
        List<RideRequest> requestsList = new ArrayList<>();
        repository.findAll().forEach(requestsList::add);
        return requestsList;
    }

    public RideRequest findRideRequestById(long id) {
        authContext.assureIsAuthenticated();
        return repository.findById(id).orElseThrow(() -> new RideRequestNotFoundException(id));
    }

    public RideRequest updateRideRequest(RideRequest rideRequest) throws IllegalAccessException {
        RideRequest saved = repository.findById(rideRequest.getId()).orElseThrow(() -> new RideRequestNotFoundException(rideRequest.getId()));
        authContext.assureHasId(saved.getUser().getId());
        addressService.updateAddress(rideRequest.getFromAddress());
        addressService.updateAddress(rideRequest.getToAddress());
        return repository.save(rideRequest);
    }

    public void deleteRideRequest(long id) throws IllegalAccessException {
        RideRequest saved = repository.findById(id).orElseThrow(() -> new RideRequestNotFoundException(id));
        authContext.assureHasId(saved.getUser().getId());
        repository.deleteById(id);
    }
}