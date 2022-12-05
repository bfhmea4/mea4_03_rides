package com.spring.webtest.database.repositories;

import com.spring.webtest.database.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {

    public List<Address> findByLocation(String location);
}
