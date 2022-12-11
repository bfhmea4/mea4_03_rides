package com.spring.webtest.controller;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.dto.RideOfferDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.RideOfferService;
import org.jose4j.jwt.MalformedClaimException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class RideOfferController {

    private final RideOfferService service;
    private final ModelMapper modelMapper;

    public RideOfferController(RideOfferService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("/api/offer")
    ResponseEntity<RideOfferDto> post(@RequestBody RideOfferDto rideOfferDto) {
        logger.info("add ride offers");

        RideOfferDto savedRideOfferDto;
        try {
            RideOffer rideOffer = modelMapper.map(rideOfferDto, RideOffer.class);
            RideOffer savedRideOffer = service.addRideOffer(rideOffer);
            savedRideOfferDto = modelMapper.map(savedRideOffer, RideOfferDto.class);
        } catch (NullPointerException | OperationNotSupportedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (rideOfferDto != null) {
            return new ResponseEntity<>(savedRideOfferDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/offers")
    ResponseEntity<List<RideOfferDto>> getAll() {
        logger.info("get all ride offers");
        List<RideOffer> rideOffers = service.getAllRideOffers();
        List<RideOfferDto> rideOfferDtos = rideOffers.stream()
                .map(user -> modelMapper.map(user, RideOfferDto.class))
                .toList();
        return new ResponseEntity<>(rideOfferDtos, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/offer/{id}")
    ResponseEntity<RideOfferDto> get(@PathVariable long id) {
        logger.info("get ride offer with id: " + id);
        RideOffer rideOffer = service.findRideOfferById(id);
        RideOfferDto rideOfferDto = modelMapper.map(rideOffer, RideOfferDto.class);
        return new ResponseEntity<>(rideOfferDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("/api/offer/{id}")
    ResponseEntity<RideOfferDto> update(@PathVariable long id, @RequestBody RideOfferDto rideOfferDto) {
        if (id != rideOfferDto.getId())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("update ride offer with id: " + id);
        RideOffer rideOffer = modelMapper.map(rideOfferDto, RideOffer.class);

        RideOffer savedRideOffer;
        try {
            savedRideOffer = service.updateRiderOffer(rideOffer);
            RideOfferDto savedRideOfferDto = modelMapper.map(savedRideOffer, RideOfferDto.class);
            return new ResponseEntity<>(savedRideOfferDto, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("/api/offer/{id}")
    ResponseEntity<?> delete(@PathVariable long id) {
        logger.info("delete ride offer with id: " + id);
        try {
            service.deleteRideOffer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalAccessException | MalformedClaimException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
