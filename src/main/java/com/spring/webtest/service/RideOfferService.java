package com.spring.webtest.service;

import com.spring.webtest.model.RideOffer;
import com.spring.webtest.repositories.RideOfferRepository;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideOfferService {
    private final RideOfferRepository repository;

    @Autowired
    public RideOfferService(RideOfferRepository repository) {
        this.repository = repository;
    }

    public RideOffer addRideOffer(RideOffer rideOffer) {
        return repository.save(rideOffer);
    }

    public List<RideOffer> findAllRideOffers() {
        return (List<RideOffer>) repository.findAll();
    }

    public RideOffer findRideOfferById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Find Offer by id: Ride offer with id: " + id + "not found"));
    }

    public RideOffer updateRiderOffer(RideOffer rideOffer) {
        repository.findById(rideOffer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update offer: Ride offer with id: " + rideOffer.getId() + "not found"));
        return repository.save(rideOffer);
    }

    public void deleteRideOfferById(long id) {
        repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Delete offer by id: Ride offer with id: " + id + "not found"));
        repository.deleteById(id);
    }


}
