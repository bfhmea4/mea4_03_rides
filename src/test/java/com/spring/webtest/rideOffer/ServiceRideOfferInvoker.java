package com.spring.webtest.rideOffer;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.dto.RideOfferDto;
import com.spring.webtest.service.RideOfferService;
import org.springframework.beans.factory.annotation.Autowired;



public class ServiceRideOfferInvoker implements RideOfferInvoker {

    @Autowired
    RideOfferService service;

    public ServiceRideOfferInvoker (RideOfferService service) {
        this.service = service;
    }




    @Override
    public RideOfferDto createOffer(RideOffer rideOffer) {
        return service.addRideOffer(rideOffer);
    }

    @Override
    public RideOfferDto getOffer(long id) {
        return service.findRideOfferById(id);
    }

    @Override
    public RideOfferDto updateOffer(RideOffer rideOffer) {
//        return service.updateRiderOffer(rideOffer);
        return new RideOfferDto();
    }

    @Override
    public void deleteOffer(long id) {
//        service.deleteRideOffer(id);

    }
}



