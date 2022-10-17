package com.spring.webtest.rideOffer;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.service.RideOfferService;
import org.springframework.beans.factory.annotation.Autowired;



public class ServiceRideOfferInvoker implements RideOfferInvoker {

    @Autowired
    RideOfferService service;

    public ServiceRideOfferInvoker (RideOfferService service) {
        this.service = service;
    }




    @Override
    public RideOffer createOffer(RideOffer rideOffer) {
        return service.addRideOffer(rideOffer);
    }

    @Override
    public RideOffer getOffer(long id) {
        return service.findRideOfferById(id);
    }

    @Override
    public RideOffer updateOffer(RideOffer rideOffer) {
        return service.updateRiderOffer(rideOffer);
    }

    @Override
    public void deleteOffer(long id) {
        service.deleteRideOfferById(id);

    }
}



