package com.spring.webtest.rideOffer;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.dto.RideOfferDto;

interface RideOfferInvoker {
    RideOfferDto createOffer(RideOffer rideOffer);

    RideOfferDto getOffer(long id);

    RideOfferDto updateOffer(RideOffer rideOffer);

    void deleteOffer(long id);
}
