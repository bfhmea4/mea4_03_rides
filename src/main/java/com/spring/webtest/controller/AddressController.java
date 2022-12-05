package com.spring.webtest.controller;

import com.spring.webtest.database.entities.Address;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/address")
    ResponseEntity<AddressDto> post(@RequestBody Address address) {
        logger.info("add ride address: " + address);
        AddressDto addressDto = service.addAddress(address);
        return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/address/{id}")
    ResponseEntity<AddressDto> get(@PathVariable long id) {
        logger.info("get address with id: " + id);
        AddressDto addressDto = service.findAddressById(id);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/address/{id}")
    ResponseEntity<AddressDto> put(@PathVariable long id, @RequestBody Address address) {
        if (id != address.getId())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            logger.info("update address with id: " + id);
            AddressDto addressDto = service.findAddressById(id);
            return new ResponseEntity<>(addressDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
//    @DeleteMapping Mapping("api/address/{id}")
//    ResponseEntity<?> delete(@PathVariable long id) {
//        logger.info("delete address with id: " + id);
//        try {
//            service.deleteAddress(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }



}
