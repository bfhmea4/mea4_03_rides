package com.spring.webtest.service;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.RideOfferRepository;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideOfferService {

    private final RideOfferRepository repository;

    private final UserService userService;

    private final AddressService addressService;


    public RideOfferService(RideOfferRepository repository, UserService userService, AddressService addressService) {
        this.repository = repository;
        this.userService = userService;
        this.addressService = addressService;
    }

    public RideOffer addRideOffer(RideOffer rideOffer) throws OperationNotSupportedException, IllegalAccessException {
        try {
            User user = userService.getById(rideOffer.getUser().getId());
            if (rideOffer.getUser() != null && rideOffer.getUser().equals(user)) {
                AddressDto fromAddress = addressService.addAddress(rideOffer.getFromAddress());
                AddressDto toAddress = addressService.addAddress(rideOffer.getToAddress());
                rideOffer.getFromAddress().setId(fromAddress.getId());
                rideOffer.getToAddress().setId(toAddress.getId());
                return repository.save(rideOffer);
            }
            throw new IllegalAccessException();
        } catch (NullPointerException e) {
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
        RideOffer saved = repository.findById(rideOffer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update offer: Ride offer with id: " + rideOffer.getId() + " not found"));
        if (rideOffer.getUser() == null || saved.getUser().getId() != rideOffer.getUser().getId()) {
            throw new IllegalAccessException();
        }
        addressService.updateAddress(rideOffer.getFromAddress());
        addressService.updateAddress(rideOffer.getToAddress());
        return repository.save(rideOffer);
    }

    public void deleteRideOffer(long id) throws IllegalAccessException {
        RideOffer saved = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Delete offer by id: Ride offer with id: " + id + " not found"));
    //TODO Check id of user logged and saved to ensure deletion only from owner of offer
//        if (rideOffer.getUser() == null || saved.getUser().getId() != rideOffer.getUser().getId()) {
//            throw new IllegalAccessException();
//        }
        repository.deleteById(saved.getId());
    }
}


