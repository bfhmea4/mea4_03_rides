package com.spring.webtest.database.repositories;

import com.spring.webtest.database.entities.RideOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideOfferRepository extends CrudRepository<RideOffer, Long> {

}
