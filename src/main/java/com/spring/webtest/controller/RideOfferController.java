package com.spring.webtest.controller;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.RideOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class RideOfferController {

    private final RideOfferService service;

    public RideOfferController(RideOfferService service) {
        this.service = service;
    }

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("/ride-offer")
    ResponseEntity<RideOffer> addRideOffer(@RequestBody RideOffer rideOffer) {
        logger.info("add ride offers");
        service.addRideOffer(rideOffer);
        return new ResponseEntity<>(rideOffer, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/ride-offer")
    ResponseEntity<List<RideOffer>> getAllRideOffers() {
        logger.info("get all ride offers");
        List<RideOffer> rideOffers;
        rideOffers = service.getAllRideOffers();
        return new ResponseEntity<>(rideOffers, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/ride-offer/{id}")
    ResponseEntity<RideOffer> getRideOfferById(@PathVariable long id) {
        logger.info("get ride offer with id: " + id);
        RideOffer rideOffer = service.findRideOfferById(id);
        return new ResponseEntity<>(rideOffer, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("/ride-offer/{id}")
    ResponseEntity<RideOffer> updateRideOffer(@PathVariable long id, @RequestBody RideOffer rideOffer) {
        if (id != rideOffer.getId())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("update ride offer with id: " + id);
        try {
            service.updateRiderOffer(rideOffer);
            return new ResponseEntity<>(rideOffer, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("/ride-offer/{id}")
    ResponseEntity<?> deleteRideOfferById(@PathVariable long id) {
        logger.info("delete ride offer with id: " + id);
        try {
            service.deleteRideOfferById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
