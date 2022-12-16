package com.spring.webtest.controller;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.dto.RideRequestDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.RideRequestService;
import org.jose4j.jwt.MalformedClaimException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class RideRequestController {

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());

    private final RideRequestService service;
    private final ModelMapper modelMapper;

    public RideRequestController(RideRequestService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("/api/requests")
    @Secured("ROLE_USER")
    ResponseEntity<RideRequestDto> addRideRequest(@RequestBody RideRequestDto rideRequestDto) {
        try {
            logger.info("add ride offers");
            RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
            RideRequest savedRideRequest = service.addRideRequest(rideRequest);
            RideRequestDto savedRideRequestDto = modelMapper.map(savedRideRequest, RideRequestDto.class);
            return new ResponseEntity<>(savedRideRequestDto, HttpStatus.CREATED);
        } catch (IllegalAccessException | NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (OperationNotSupportedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/requests")
    @Secured("ROLE_USER")
    ResponseEntity<List<RideRequestDto>> getAllRideRequests() {
        logger.info("get all ride offers");
        List<RideRequest> rideRequests = service.findAllRideRequests();
        List<RideRequestDto> rideRequestDtos = rideRequests.stream()
                .map(user -> modelMapper.map(user, RideRequestDto.class))
                .toList();
        return new ResponseEntity<>(rideRequestDtos, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("/api/requests/{id}")
    @Secured("ROLE_USER")
    ResponseEntity<RideRequestDto> getRideRequestById(@PathVariable int id) {
        logger.info("get ride offer with id: " + id);
        RideRequest rideRequest = service.findRideRequestById(id);
        RideRequestDto rideRequestDto = modelMapper.map(rideRequest, RideRequestDto.class);
        return new ResponseEntity<>(rideRequestDto, HttpStatus.OK);

    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("/api/requests/{id}")
    @Secured("ROLE_USER")
    ResponseEntity<RideRequestDto> updateRideRequest(@RequestBody RideRequestDto rideRequestDto) {
        logger.info("update ride offer with id: " + rideRequestDto.getId());
        try {
            RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
            RideRequest savedRideRequest = service.updateRideRequest(rideRequest);
            RideRequestDto savedRideRequestDto = modelMapper.map(savedRideRequest, RideRequestDto.class);
            return new ResponseEntity<>(savedRideRequestDto, HttpStatus.OK);
        } catch (IllegalAccessException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("/api/requests/{id}")
    @Secured("ROLE_USER")
    ResponseEntity<?> deleteRideRequestById(@PathVariable int id) {
        logger.info("delete ride offer with id: " + id);
        try {
            service.deleteRideRequest(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalAccessException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
