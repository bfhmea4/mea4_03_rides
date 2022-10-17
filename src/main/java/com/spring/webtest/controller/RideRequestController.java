package com.spring.webtest.controller;

import com.google.gson.Gson;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.service.RideRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class RideRequestController {

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());

    private static final Gson gson = new Gson();

    private final RideRequestService service;


    public RideRequestController(RideRequestService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("/api/requests")
    ResponseEntity<RideRequest> addRideRequest(@RequestBody RideRequest rideRequest) {
        logger.info("add ride offers");
        rideRequest = service.addRideRequest(rideRequest);
        return new ResponseEntity<>(rideRequest, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/requests")
    ResponseEntity<List<RideRequest>> getAllRideRequests() {
        logger.info("get all ride offers");
        List<RideRequest> rideRequests;
        rideRequests = service.findAllRideRequests();
        return new ResponseEntity<>(rideRequests, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/requests/{id}")
    ResponseEntity<RideRequest> getRideRequestById(@PathVariable int id) {
        logger.info("get ride offer with id: " + id);
        RideRequest rideRequest = service.findRideRequestById(id);
        return new ResponseEntity<>(rideRequest, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("/api/requests/{id}")
    ResponseEntity<RideRequest> updateRideRequest(@RequestBody RideRequest rideRequest) {
        logger.info("update ride offer with id: " + rideRequest.getId());
        service.updateRideRequest(rideRequest);
        return new ResponseEntity<>(rideRequest, HttpStatus.OK);
    }


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("/api/requests/{id}")
    ResponseEntity<?> deleteRideRequestById(@PathVariable int id) {
        logger.info("delete ride offer with id: " + id);
        service.deleteRideRequestById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
