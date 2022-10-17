package com.spring.webtest.service;

import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.repositories.RideRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideRequestService {
    private final RideRequestRepository repository;

    @Autowired
    public RideRequestService(RideRequestRepository repository) {
        this.repository = repository;
    }

    public RideRequest addRideRequest(RideRequest rideRequest) {
        return repository.save(rideRequest);
    }

    public List<RideRequest> findAllRideRequests() {
        return (List<RideRequest>) repository.findAll();
    }

    public RideRequest findRideRequestById(long id) {
        return repository.findById(id).orElse(null);
    }

    public RideRequest updateRideRequest(RideRequest rideRequest) {
        repository.findById(rideRequest.getId()).orElseThrow();
        return repository.save(rideRequest);
    }

    public void deleteRideRequestById(long id) {
        repository.findById(id).orElseThrow();
        repository.deleteById(id);
    }


}