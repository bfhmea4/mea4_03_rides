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

    private final RideOfferRepository repository;

    private final UserService userService;


    public RideOfferService(RideOfferRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public RideOfferDto addRideOffer(RideOffer rideOffer) {
        UserDto userDto = userService.getById(rideOffer.getUser().getId());
        if (rideOffer.getUser() != null && userDto != null && userService.userToDto(rideOffer.getUser()).equals(userDto)) {
            return rideOfferToDto(repository.save(rideOffer));
        }
        return null;
    }

    public List<RideOfferDto> getAllRideOffers() {
        List<RideOfferDto> offersList = new ArrayList<>();
        repository.findAll().forEach(rideOffer -> offersList.add(rideOfferToDto(rideOffer)));
        return offersList;
    }

    public RideOfferDto findRideOfferById(long id) {
        return rideOfferToDto(repository.findById(id).orElse(null));
    }

    public RideOfferDto updateRiderOffer(RideOffer rideOffer) throws IllegalAccessException {
        RideOffer saved = repository.findById(rideOffer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update offer: Ride offer with id: " + rideOffer.getId() + " not found"));
        if (saved.getUser().getId() != rideOffer.getUser().getId()) {
            throw new IllegalAccessException();
        }
        return rideOfferToDto(repository.save(rideOffer));
    }

    public void deleteRideOffer(RideOffer rideOffer) throws IllegalAccessException {
        RideOffer saved = repository.findById(rideOffer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Delete offer by id: Ride offer with id: " + rideOffer.getId() + " not found"));
        if (saved.getUser().getId() != rideOffer.getUser().getId()) {
            throw new IllegalAccessException();
        }
        repository.deleteById(rideOffer.getId());
    }

    public RideOfferDto rideOfferToDto(RideOffer offer) {
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


