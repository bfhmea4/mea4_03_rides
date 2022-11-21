package com.spring.webtest.service;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.repositories.RideRequestRepository;
import com.spring.webtest.dto.RideOfferDto;
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

    @Autowired
    public RideRequestService(RideRequestRepository repository) {
        this.repository = repository;
    }

    public RideRequestDto addRideRequest(RideRequest rideRequest) {
        return rideRequestDto(repository.save(rideRequest));
    }

    public List<RideRequestDto> findAllRideRequests() {
        List<RideRequestDto> requestsList = new ArrayList<>();

        repository.findAll().forEach(rideRequest -> {
            requestsList.add(rideRequestDto(rideRequest));
        });
        return requestsList;
    }

    public RideRequestDto findRideRequestById(long id) {
        return rideRequestDto(repository.findById(id).orElse(null));
    }

    public RideRequestDto updateRideRequest(RideRequest rideRequest) throws IllegalAccessException {
        RideRequest saved = repository.findById(rideRequest.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + rideRequest.getId() + " not found"));
        if (saved.getUser().getId() != rideRequest.getUser().getId()) {
            throw new IllegalAccessException();
        }
        return rideRequestDto(repository.save(rideRequest));
    }

    public void deleteRideRequestById(long id) {
        repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Update Request: Ride Request with id: " + id + " not found"));
        repository.deleteById(id);
    }

    private RideRequestDto rideRequestDto(RideRequest request) {
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