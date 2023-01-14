package com.spring.webtest.rideOffer;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.dto.RideOfferDto;
import com.spring.webtest.service.RideOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RideOfferServiceInvoker implements RideOfferInvoker {

    @Autowired
    private RideOfferService service;

    @Override
    public RideOfferDto createOffer(RideOffer rideOffer) throws OperationNotSupportedException, IllegalAccessException {
        return null;
    }

    @Override
    public RideOfferDto getOffer(long id) {
        return null;
    }

    @Override
    public RideOfferDto updateOffer(RideOffer rideOffer) throws IllegalAccessException {
        return null;
    }

    @Override
    public void deleteOffer(long id) throws IllegalAccessException {
        service.deleteRideOffer(id);
    }
}



