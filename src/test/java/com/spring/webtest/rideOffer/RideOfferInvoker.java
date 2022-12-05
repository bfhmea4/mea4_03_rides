package com.spring.webtest.rideOffer;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.dto.RideOfferDto;

public interface RideOfferInvoker {
    RideOfferDto createOffer(RideOffer rideOffer) throws OperationNotSupportedException, IllegalAccessException;

    RideOfferDto getOffer(long id);

    RideOfferDto updateOffer(RideOffer rideOffer) throws IllegalAccessException;

    void deleteOffer(long rideOffer) throws IllegalAccessException;
}
