package com.spring.webtest.service;


import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.repositories.AddressRepository;
import com.spring.webtest.dto.AddressDto;
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

    public AddressDto addAddress(Address address) {
        return addressToDto(repository.save(address));
    }

    public AddressDto findAddressById(long id) {
        return addressToDto(repository.findById(id).orElse(null));
    }

    public List<AddressDto> findAddressesByLocation(String location) {
        List<AddressDto> addresses = new ArrayList<>();
        repository.findByLocation(location).forEach(address ->  addresses.add(addressToDto(address)));
        return addresses;
    }

    public AddressDto updateAddress(Address address) {
        Address saved = repository.findById(address.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update address: Address " + address + " not found"));
        return addressToDto(repository.save(address));
    }

    public void deleteAddress(long id) {
        Address saved = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Delete address: Address with id " + id + " not found"));
        repository.deleteById(id);
    }

    public AddressDto addressToDto(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode(),
                address.getLocation()
        );
    }
}
