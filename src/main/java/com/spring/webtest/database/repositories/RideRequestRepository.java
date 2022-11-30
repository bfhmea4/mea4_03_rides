package com.spring.webtest.database.repositories;

import com.spring.webtest.database.entities.RideRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends CrudRepository<RideRequest, Long> {
}
