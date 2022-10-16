package com.spring.webtest.controller;

import com.google.gson.Gson;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.service.RideOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

public class RideOfferController {

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());

    private static final Gson gson = new Gson();

    private final RideOfferService service;


    public RideOfferController(RideOfferService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("/ride-offer")
    @ResponseBody
    ResponseEntity<RideOffer> addRideOffer(RideOffer rideOffer) {
        logger.info("add ride offers");
        service.addRideOffer(rideOffer);
        return new ResponseEntity<>(rideOffer, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/ride-offer")
    @ResponseBody
    ResponseEntity<List<RideOffer>> getAllRideOffers() {
        logger.info("get all ride offers");
        List<RideOffer> rideOffers;
        rideOffers = service.getAllRideOffers();
        return new ResponseEntity<>(rideOffers, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/ride-offer/{id}")
    @ResponseBody
    ResponseEntity<RideOffer> getRideOfferById(@PathVariable int id) {
        logger.info("get ride offer with id: " + id);
        RideOffer rideOffer = service.findRideOfferById(id);
        return new ResponseEntity<>(rideOffer, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("/ride-offer")
    @ResponseBody
    ResponseEntity<RideOffer> updateRideOffer(RideOffer rideOffer) {
        logger.info("update ride offer with id: " + rideOffer.getId());
        service.updateRiderOffer(rideOffer);
        return new ResponseEntity<>(rideOffer, HttpStatus.OK);
    }


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("/ride-offer/{id}")
    @ResponseBody
    ResponseEntity<?> deleteRideOfferById(@PathVariable int id) {
        logger.info("delete ride offer with id: " + id);
        service.deleteRideOfferById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
