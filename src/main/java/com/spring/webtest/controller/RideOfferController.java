package com.spring.webtest.controller;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.dto.RideOfferDto;
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
    @PostMapping("/api/offer")
    ResponseEntity<RideOfferDto> post(@RequestBody RideOffer rideOffer) {
            logger.info("add ride offers");
            RideOfferDto rideOfferDto = service.addRideOffer(rideOffer);
            if(rideOfferDto != null) {
            return new ResponseEntity<>(rideOfferDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/offers")
    ResponseEntity<List<RideOfferDto>> getAll() {
        logger.info("get all ride offers");
        List<RideOfferDto> rideOffers;
        rideOffers = service.getAllRideOffers();
        return new ResponseEntity<>(rideOffers, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/offer/{id}")
    ResponseEntity<RideOfferDto> get(@PathVariable long id) {
        logger.info("get ride offer with id: " + id);
        RideOfferDto rideOfferDto = service.findRideOfferById(id);
        return new ResponseEntity<>(rideOfferDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("/api/offer/{id}")
    ResponseEntity<RideOfferDto> update(@PathVariable long id, @RequestBody RideOffer rideOffer) {
        if (id != rideOffer.getId())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("update ride offer with id: " + id);
        try {
            RideOfferDto rideOfferDto = service.updateRiderOffer(rideOffer);
            return new ResponseEntity<>(rideOfferDto, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("/api/offer/{id}")
    ResponseEntity<?> delete(@PathVariable long id) {
        logger.info("delete ride offer with id: " + id);
        try {
            service.deleteRideOfferById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
