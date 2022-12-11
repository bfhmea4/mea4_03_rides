package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.context.UserContext;
import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.RideOfferRepository;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RideOfferService {

    private final RideOfferRepository repository;

    private final UserService userService;
    private final AuthService authService;
    private final AddressService addressService;

    @Autowired
    private UserContext userContext;


    public RideOfferService(RideOfferRepository repository, UserService userService, AddressService addressService, AuthService authService) {
        this.repository = repository;
        this.userService = userService;
        this.addressService = addressService;
        this.authService = authService;
    }

    public RideOffer addRideOffer(RideOffer rideOffer) throws IllegalAccessException, OperationNotSupportedException {
        checkIfUserIsAuthorized(rideOffer);
        try {
            Address fromAddress = addressService.addAddress(rideOffer.getFromAddress());
            Address toAddress = addressService.addAddress(rideOffer.getToAddress());
            rideOffer.getFromAddress().setId(fromAddress.getId());
            rideOffer.getToAddress().setId(toAddress.getId());
            return repository.save(rideOffer);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new OperationNotSupportedException();
        }
    }

    public List<RideOffer> getAllRideOffers() {
        List<RideOffer> offersList = new ArrayList<>();
        repository.findAll().forEach(offersList::add);
        return offersList;
    }

    public RideOffer findRideOfferById(long id) {
        return repository.findById(id).orElse(null);
    }

    public RideOffer updateRiderOffer(RideOffer rideOffer) throws IllegalAccessException {
        checkIfUserIsAuthorized(rideOffer);
        RideOffer saved = repository.findById(rideOffer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update offer: Ride offer with id: " + rideOffer.getId() + " not found"));
        addressService.updateAddress(rideOffer.getFromAddress());
        addressService.updateAddress(rideOffer.getToAddress());
        return repository.save(rideOffer);
    }

    public void deleteRideOffer(long id) throws IllegalAccessException, MalformedClaimException {
        RideOffer saved = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Delete offer by id: Ride offer with id: " + id + " not found"));
        checkIfUserIsAuthorized(saved);
        repository.deleteById(saved.getId());
    }

    private void checkIfUserIsAuthorized(RideOffer rideOffer) throws IllegalAccessException {
        if (Objects.isNull(rideOffer)) {
            throw new IllegalAccessException();
        }
        User savedUser = userService.getById(rideOffer.getUser().getId());
        User contextUser = userContext.getUser();
        if (!savedUser.equals(contextUser)) {
            throw new IllegalAccessException();
        }
    }
}


