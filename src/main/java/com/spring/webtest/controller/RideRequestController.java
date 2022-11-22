package com.spring.webtest.controller;

import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.dto.RideRequestDto;
import com.spring.webtest.service.RideRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class RideRequestController {

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());

    private final RideRequestService service;



    public RideRequestController(RideRequestService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("/api/requests")
    ResponseEntity<RideRequestDto> addRideRequest(@RequestBody RideRequest rideRequest) {
        logger.info("add ride offers");
        RideRequestDto rideRequestDto = service.addRideRequest(rideRequest);
        if (rideRequestDto != null) {
            return new ResponseEntity<>(rideRequestDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/requests")
    ResponseEntity<List<RideRequestDto>> getAllRideRequests() {
        logger.info("get all ride offers");
        List<RideRequestDto> rideRequests;
        rideRequests = service.findAllRideRequests();
        return new ResponseEntity<>(rideRequests, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/requests/{id}")
    ResponseEntity<RideRequestDto> getRideRequestById(@PathVariable int id) {
        logger.info("get ride offer with id: " + id);
        RideRequestDto rideRequestDto = service.findRideRequestById(id);
        return new ResponseEntity<>(rideRequestDto, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("/api/requests/{id}")
    ResponseEntity<RideRequestDto> updateRideRequest(@RequestBody RideRequest rideRequest) {
        logger.info("update ride offer with id: " + rideRequest.getId());
        RideRequestDto rideRequestDto = service.updateRideRequest(rideRequest);
        return new ResponseEntity<>(rideRequestDto, HttpStatus.OK);
    }


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("/api/requests/{id}")
    ResponseEntity<?> deleteRideRequestById(@PathVariable int id) {
        logger.info("delete ride offer with id: " + id);
        service.deleteRideRequestById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
