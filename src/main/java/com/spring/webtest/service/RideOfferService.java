package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.exception.RideOfferNotFoundException;
import com.spring.webtest.security.AuthContext;
import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.repositories.RideOfferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideOfferService {

    private final RideOfferRepository repository;
    private final AddressService addressService;
    private final AuthContext authContext;


    public RideOfferService(RideOfferRepository repository, AddressService addressService, AuthContext authContext) {
        this.repository = repository;
        this.addressService = addressService;
        this.authContext = authContext;
    }

    public RideOffer addRideOffer(RideOffer rideOffer) throws IllegalAccessException, OperationNotSupportedException {
        authContext.assureHasId(rideOffer.getUser().getId());
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
        authContext.assureIsAuthenticated();
        List<RideOffer> offersList = new ArrayList<>();
        repository.findAll().forEach(offersList::add);
        return offersList;
    }

    public RideOffer findRideOfferById(long id) {
        authContext.assureIsAuthenticated();
        return repository.findById(id).orElseThrow(() -> new RideOfferNotFoundException(id));
    }

    public RideOffer updateRiderOffer(RideOffer rideOffer) throws IllegalAccessException {
        RideOffer saved = repository.findById(rideOffer.getId()).orElseThrow(() -> new RideOfferNotFoundException(rideOffer.getId()));
        authContext.assureHasId(saved.getUser().getId());
        addressService.updateAddress(rideOffer.getFromAddress());
        addressService.updateAddress(rideOffer.getToAddress());
        return repository.save(rideOffer);
    }

    public void deleteRideOffer(long id) throws IllegalAccessException {
        authContext.assureHasId(id);
        repository.findById(id).orElseThrow(() -> new RideOfferNotFoundException(id));
        repository.deleteById(id);
    }
}