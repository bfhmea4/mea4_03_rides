package com.spring.webtest.service;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.repositories.RideOfferRepository;
import com.spring.webtest.dto.RideOfferDto;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideOfferService {

    private RideOfferRepository repository;


    public RideOfferService(RideOfferRepository repository) {
        this.repository = repository;
    }

    public RideOfferDto addRideOffer(RideOffer rideOffer) {
        return rideOfferToDto(repository.save(rideOffer));
    }

    public List<RideOfferDto> getAllRideOffers() {
        List<RideOfferDto> offersList = new ArrayList<>();
        repository.findAll().forEach(rideOffer -> {
            offersList.add(rideOfferToDto(rideOffer));
        });
        return offersList;
    }

    public RideOfferDto findRideOfferById(long id) {
        return rideOfferToDto(repository.findById(id).orElse(null));
    }

    public RideOfferDto updateRiderOffer(RideOffer rideOffer) {
        repository.findById(rideOffer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update offer: Ride offer with id: " + rideOffer.getId() + " not found"));
        return rideOfferToDto(repository.save(rideOffer));
    }

    public void deleteRideOfferById(long id) {
        repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Delete offer by id: Ride offer with id: " + id + " not found"));
        repository.deleteById(id);
    }

    private RideOfferDto rideOfferToDto(RideOffer offer) {
        if (offer == null) {
            return null;
        }
        return new RideOfferDto(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                new UserDto(offer.getUser().getId(),
                        offer.getUser().getFirstName(),
                        offer.getUser().getLastName(),
                        offer.getUser().getEmail(),
                        offer.getUser().getAddress()));
    }
}


