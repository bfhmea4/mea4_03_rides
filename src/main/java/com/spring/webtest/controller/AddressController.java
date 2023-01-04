package com.spring.webtest.controller;

import com.spring.webtest.database.entities.Address;
import com.spring.webtest.dto.AddressDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class AddressController {

    private final AddressService service;
    private final ModelMapper modelMapper;

    public AddressController(AddressService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/address")
    @Secured("ROLE_USER")
    ResponseEntity<AddressDto> post(@RequestBody AddressDto addressDto) {
        logger.info("add ride address: " + addressDto);
        Address address = modelMapper.map(addressDto, Address.class);
        Address savedAddress = service.addAddress(address);
        AddressDto savedAddressDto = modelMapper.map(savedAddress, AddressDto.class);
        return new ResponseEntity<>(savedAddressDto, HttpStatus.CREATED);
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/address/{id}")
    @Secured("ROLE_USER")
    ResponseEntity<AddressDto> get(@PathVariable long id) {
        logger.info("get address with id: " + id);
        Address address = service.findAddressById(id);
        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/address/{id}")
    @Secured("ROLE_USER")
    ResponseEntity<AddressDto> put(@PathVariable long id, @RequestBody AddressDto addressDto) {
        if (id != addressDto.getId())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("update address with id: " + id);
        Address address = modelMapper.map(addressDto, Address.class);
        Address savedAddress = service.updateAddress(address);
        AddressDto savedAddressDto = modelMapper.map(savedAddress, AddressDto.class);
        return new ResponseEntity<>(savedAddressDto, HttpStatus.OK);
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
