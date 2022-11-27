package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.repositories.RideRequestRepository;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.dto.RideRequestDto;
import com.spring.webtest.dto.UserDto;
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

    public RideRequestDto addRideRequest(RideRequest rideRequest) throws IllegalAccessException, OperationNotSupportedException {
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

    public RideRequestDto updateRideRequest(RideRequest rideRequest) throws IllegalAccessException {
        RideRequest saved = repository.findById(rideRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + rideRequest.getId() + " not found"));
        if (rideRequest.getUser() == null || saved.getUser().getId() != rideRequest.getUser().getId()) {
            throw new IllegalAccessException();
        }
        addressService.updateAddress(rideRequest.getFromAddress());
        addressService.updateAddress(rideRequest.getToAddress());
        return rideRequestToDto(repository.save(rideRequest));
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