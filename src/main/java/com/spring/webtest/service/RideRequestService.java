package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.context.UserContext;
import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.RideRequestRepository;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RideRequestService {
    private final RideRequestRepository repository;
    private final UserService userService;
    private final AuthService authService;
    private final AddressService addressService;

    @Autowired
    private UserContext userContext;

    @Autowired
    public RideRequestService(RideRequestRepository repository, UserService userService, AddressService addressService, AuthService authService) {
        this.repository = repository;
        this.userService = userService;
        this.addressService = addressService;
        this.authService = authService;
    }

    public RideRequest addRideRequest(RideRequest rideRequest) throws IllegalAccessException, OperationNotSupportedException, MalformedClaimException {
        checkIfUserIsAuthorized(rideRequest);
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
        List<RideRequest> requestsList = new ArrayList<>();
        repository.findAll().forEach(requestsList::add);
        return requestsList;
    }

    public RideRequest findRideRequestById(long id) {
        return repository.findById(id).orElse(null);
    }

    public RideRequest updateRideRequest(RideRequest rideRequest) throws IllegalAccessException, MalformedClaimException {
        checkIfUserIsAuthorized(rideRequest);
        RideRequest saved = repository.findById(rideRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + rideRequest.getId() + " not found"));
        if (rideRequest.getUser() == null || saved.getUser().getId() != rideRequest.getUser().getId()) {
            throw new IllegalAccessException();
        }
        addressService.updateAddress(rideRequest.getFromAddress());
        addressService.updateAddress(rideRequest.getToAddress());
        return repository.save(rideRequest);
    }

    public void deleteRideRequest(long id) throws IllegalAccessException, MalformedClaimException {
        RideRequest saved = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + id + " not found"));
        checkIfUserIsAuthorized(saved);
        repository.deleteById(saved.getId());
    }

    private void checkIfUserIsAuthorized(RideRequest rideRequest) throws IllegalAccessException {
        if (Objects.isNull(rideRequest)) {
            throw new IllegalAccessException();
        }
        User savedUser = userService.getById(rideRequest.getUser().getId());
        User contextUser = userContext.getUser();
        if (!savedUser.equals(contextUser)) {
            throw new IllegalAccessException();
        }
    }

}