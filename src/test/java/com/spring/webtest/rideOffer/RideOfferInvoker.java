package com.spring.webtest.rideOffer;

import com.spring.webtest.database.entities.RideOffer;

interface RideOfferInvoker {
    RideOffer createRequest(RideOffer rideOffer);

    RideOffer getRequest(long id);

    RideOffer updateRequest(RideOffer rideOffer);

    void deleteRequest(long id);
}
