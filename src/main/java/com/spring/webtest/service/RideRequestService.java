package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.repositories.RideRequestRepository;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.dto.RideRequestDto;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideRequestService {
    private final RideRequestRepository repository;
    private final UserService userService;
    private final AuthService authService;

    private final AddressService addressService;



    @Autowired
    public RideRequestService(RideRequestRepository repository, UserService userService, AddressService addressService, AuthService authService) {
        this.repository = repository;
        this.userService = userService;
        this.addressService = addressService;
        this.authService = authService;
    }

    public RideRequestDto addRideRequest(RideRequest rideRequest, String token) throws IllegalAccessException, OperationNotSupportedException, MalformedClaimException {
        if (this.authService.tokenIsValid(token)) {
            throw new IllegalAccessException();
        }
        Long userId = authService.getIdFromToken(token);
        if (userId != rideRequest.getUser().getId()) {
            throw new IllegalAccessException();
        }
        try {
            UserDto userDto = userService.getById(rideRequest.getUser().getId());
            if (userService.userToDto(rideRequest.getUser()).equals(userDto)) {
                addressService.addAddress(rideRequest.getFromAddress());
                addressService.addAddress(rideRequest.getToAddress());
                return rideRequestToDto(repository.save(rideRequest));
            }
            throw new IllegalAccessException();
        } catch (NullPointerException e) {
            throw new OperationNotSupportedException();
        }
    }

    public List<RideRequestDto> findAllRideRequests() {
        List<RideRequestDto> requestsList = new ArrayList<>();
        repository.findAll().forEach(rideRequest -> requestsList.add(rideRequestToDto(rideRequest)));
        return requestsList;
    }

    public RideRequestDto findRideRequestById(long id) {
        return rideRequestToDto(repository.findById(id).orElse(null));
    }

    public RideRequestDto updateRideRequest(RideRequest rideRequest, String token) throws IllegalAccessException, MalformedClaimException {
        if (this.authService.tokenIsValid(token)) {
            throw new IllegalAccessException();
        }
        Long userId = authService.getIdFromToken(token);
        if (userId != rideRequest.getUser().getId()) {
            throw new IllegalAccessException();
        }
        RideRequest saved = repository.findById(rideRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + rideRequest.getId() + " not found"));
        if (rideRequest.getUser() == null || saved.getUser().getId() != rideRequest.getUser().getId()) {
            throw new IllegalAccessException();
        }
        addressService.updateAddress(rideRequest.getFromAddress());
        addressService.updateAddress(rideRequest.getToAddress());
        return rideRequestToDto(repository.save(rideRequest));
    }

    public void deleteRideRequest(long id, String token) throws IllegalAccessException, MalformedClaimException {
        if (this.authService.tokenIsValid(token)) {
            throw new IllegalAccessException();
        }
        RideRequest saved = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + id + " not found"));
        Long userId = authService.getIdFromToken(token);
        if (userId != saved.getUser().getId()) {
            throw new IllegalAccessException();
        }
        repository.deleteById(saved.getId());
    }

    private RideRequestDto rideRequestToDto(RideRequest request) {
        if (request == null) {
            return null;
        }
        return new RideRequestDto(
                request.getId(),
                request.getTitle(),
                request.getDescription(),
                new UserDto(request.getUser().getId(),
                        request.getUser().getFirstName(),
                        request.getUser().getLastName(),
                        request.getUser().getEmail(),
                        request.getUser().getAddress()),
                new AddressDto(request.getFromAddress().getId(),
                        request.getFromAddress().getStreet(),
                        request.getFromAddress().getHouseNumber(),
                        request.getFromAddress().getPostalCode(),
                        request.getFromAddress().getLocation()),
                new AddressDto(request.getToAddress().getId(),
                        request.getToAddress().getStreet(),
                        request.getToAddress().getHouseNumber(),
                        request.getToAddress().getPostalCode(),
                        request.getToAddress().getLocation()));
    }
}