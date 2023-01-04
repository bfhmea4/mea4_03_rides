package com.spring.webtest.service;


import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.repositories.AddressRepository;
import com.spring.webtest.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address addAddress(Address address) {
        return repository.save(address);
    }

    public Address findAddressById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Address> findAddressesByLocation(String location) {
        List<Address> addresses = new ArrayList<>();
        repository.findByLocation(location).forEach(addresses::add);
        return addresses;
    }

    public Address updateAddress(Address address) {
        repository.findById(address.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update address: Address " + address + " not found"));
        return repository.save(address);
    }

//    public void deleteAddress(long id) {
//        Address saved = repository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Delete address: Address with id " + id + " not found"));
//        repository.deleteById(id);
//    }
}
