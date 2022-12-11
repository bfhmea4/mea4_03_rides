package com.spring.webtest.service;

import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.RideOfferRepository;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RideOfferService {

    private final RideOfferRepository repository;

    private final UserService userService;
    private final AuthService authService;
    private final AddressService addressService;


    public RideOfferService(RideOfferRepository repository, UserService userService, AddressService addressService, AuthService authService) {
        this.repository = repository;
        this.userService = userService;
        this.addressService = addressService;
        this.authService = authService;
    }

    public RideOffer addRideOffer(RideOffer rideOffer, String token) throws IllegalAccessException, MalformedClaimException {
        boolean tokenIsValid = authService.tokenIsValid(token);
        if (!tokenIsValid) {
            throw new IllegalAccessException("Could not validate Token");
        }
        User user = userService.getById(rideOffer.getUser().getId());
        if (rideOffer.getUser() != null && rideOffer.getUser().equals(user)) {
            Address fromAddress = addressService.addAddress(rideOffer.getFromAddress());
            Address toAddress = addressService.addAddress(rideOffer.getToAddress());
            rideOffer.getFromAddress().setId(fromAddress.getId());
            rideOffer.getToAddress().setId(toAddress.getId());
            return repository.save(rideOffer);
        }
        throw new IllegalAccessException();
    }

    public List<RideOffer> getAllRideOffers() {
        List<RideOffer> offersList = new ArrayList<>();
        repository.findAll().forEach(offersList::add);
        return offersList;
    }

    public RideOffer findRideOfferById(long id) {
        return repository.findById(id).orElse(null);
    }

    public RideOffer updateRiderOffer(RideOffer rideOffer, String token) throws IllegalAccessException, MalformedClaimException {
        if (!this.authService.tokenIsValid(token)) {
            throw new IllegalAccessException();
        }
        Long userId = this.authService.getIdFromToken(token);
        if (userId != rideOffer.getUser().getId()) {
            throw new IllegalAccessException();
        }
        RideOffer saved = repository.findById(rideOffer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update offer: Ride offer with id: " + rideOffer.getId() + " not found"));
        if (rideOffer.getUser() == null || saved.getUser().getId() != rideOffer.getUser().getId()) {
            throw new IllegalAccessException();
        }
        addressService.updateAddress(rideOffer.getFromAddress());
        addressService.updateAddress(rideOffer.getToAddress());
        return repository.save(rideOffer);
    }

    public void deleteRideOffer(long id, String token) throws IllegalAccessException, MalformedClaimException {
        if (!this.authService.tokenIsValid(token)) {
            throw new IllegalAccessException();
        }
        Long userId = this.authService.getIdFromToken(token);
        RideOffer saved = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Delete offer by id: Ride offer with id: " + id + " not found"));
        if (userId != saved.getUser().getId()) {
            throw new IllegalAccessException();
        }
        repository.deleteById(saved.getId());
    }
}


