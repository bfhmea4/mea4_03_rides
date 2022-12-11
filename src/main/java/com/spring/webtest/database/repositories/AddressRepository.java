package com.spring.webtest.database.repositories;

import com.spring.webtest.database.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Iterable<Address> findByLocation(String location);
}
