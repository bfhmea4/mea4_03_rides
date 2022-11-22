package com.spring.webtest.service;

import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.repositories.RideRequestRepository;
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


    @Autowired
    public RideRequestService(RideRequestRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public RideRequestDto addRideRequest(RideRequest rideRequest) {
        UserDto userDto = userService.getById(rideRequest.getUser().getId());
        if (rideRequest.getUser() != null && userDto != null && userService.userToDto(rideRequest.getUser()).equals(userDto)) {
            return rideRequestToDto(repository.save(rideRequest));
        }
        return null;
    }

    public List<RideRequestDto> findAllRideRequests() {
        List<RideRequestDto> requestsList = new ArrayList<>();
        repository.findAll().forEach(rideRequest -> requestsList.add(rideRequestToDto(rideRequest)));
        return requestsList;
    }

    public RideRequestDto findRideRequestById(long id) {
        return rideRequestToDto(repository.findById(id).orElse(null));
    }

    public RideRequestDto updateRideRequest(RideRequest rideRequest) {
        repository.findById(rideRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + rideRequest.getId() + " not found"));
        return rideRequestToDto(repository.save(rideRequest));
    }

    public void deleteRideRequestById(long id) {
        repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + id + " not found"));
        repository.deleteById(id);
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
                        request.getUser().getAddress()));
    }
}