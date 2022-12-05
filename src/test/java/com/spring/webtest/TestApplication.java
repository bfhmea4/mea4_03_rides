package com.spring.webtest;

import com.spring.webtest.rideOffer.RideOfferInvoker;
import com.spring.webtest.rideOffer.RideOfferServiceInvoker;
import com.spring.webtest.rideOffer.RideOfferWebClientInvoker;
import com.spring.webtest.rideRequest.RideRequestInvoker;
import com.spring.webtest.rideRequest.RideRequestServiceInvoker;
import com.spring.webtest.rideRequest.RideRequestWebClientInvoker;
import com.spring.webtest.user.UserInvoker;
import com.spring.webtest.user.UserServiceInvoker;
import com.spring.webtest.user.UserWebClientInvoker;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class TestApplication {

    @Bean(name="userInvoker")
    @Profile("servicetests")
    public UserInvoker userServiceInvoker(){
        return new UserServiceInvoker();
    }

    @Bean(name="rideRequestInvoker")
    @Profile("servicetests")
    public RideRequestInvoker rideRequestServiceInvoker(){
        return new RideRequestServiceInvoker();
    }

    @Bean(name="rideOfferInvoker")
    @Profile("servicetests")
    public RideOfferInvoker rideOfferServiceInvoker(){
        return new RideOfferServiceInvoker();
    }

    @Bean(name="userInvoker")
    @Profile("webtests")
    public UserInvoker userInvoker(){
        return new UserWebClientInvoker();
    }

    @Bean(name="rideRequestInvoker")
    @Profile("webtests")
    public RideRequestInvoker rideRequestInvoker(){
        return new RideRequestWebClientInvoker();
    }

    @Bean(name="rideOfferInvoker")
    @Profile("webtests")
    public RideOfferInvoker rideOfferInvoker(){
        return new RideOfferWebClientInvoker();
    }
}