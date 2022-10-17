package com.spring.webtest.database.repositories;

import com.spring.webtest.database.entities.RideRequest;
import org.springframework.data.repository.CrudRepository;

public interface RideRequestRepository extends CrudRepository<RideRequest, Long> {
}
