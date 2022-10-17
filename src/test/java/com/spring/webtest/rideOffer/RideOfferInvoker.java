package com.spring.webtest.rideOffer;

import com.spring.webtest.database.entities.RideOffer;

interface RideOfferInvoker {
    RideOffer createOffer(RideOffer rideOffer);

    RideOffer getOffer(long id);

    RideOffer updateOffer(RideOffer rideOffer);

    void deleteOffer(long id);
}
