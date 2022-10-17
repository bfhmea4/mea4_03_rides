package com.spring.webtest.rideOffer;

import com.spring.webtest.WebTestApplication;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.service.RideOfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = {WebTestApplication.class, RideOfferService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RideOfferTests {
    @Value("${useRestMode:true}")
    private boolean useRestMode;

    @Autowired
    private RideOfferService service;

    private RideOfferInvoker rideOfferInvoker;

    @BeforeAll
    void setup() {
        if (useRestMode) {
            this.rideOfferInvoker = WebClientRideOfferInvoker.mockServer(service);
        } else {
            this.rideOfferInvoker = new ServiceRideOfferInvoker(service);
        }
    }

    @Test
    public void testShouldReturnNull() {
        assertEquals(null, rideOfferInvoker.getOffer(5465465));
    }

    @Test
    public void createRideOfferTest() {
        RideOffer rideOffer = new RideOffer("Create Test", "I'm new!");
        RideOffer created = rideOfferInvoker.createOffer(rideOffer);
        RideOffer getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getTitle(), getCreated.getTitle());
    }

    @Test
    public void updateRideOfferTest() {
        RideOffer rideOffer = new RideOffer("OfferToUpdate", "Need Update");
        RideOffer created = rideOfferInvoker.createOffer(rideOffer);
        RideOffer newRideOffer = new RideOffer(created.getId(), "UpdatedOffer", "Updated!");
        rideOfferInvoker.updateOffer(newRideOffer);
        RideOffer updated = rideOfferInvoker.getOffer(newRideOffer.getId());
        assertEquals(updated.getId(), newRideOffer.getId());
        assertEquals("UpdatedOffer", updated.getTitle());
        assertEquals("Updated!",updated.getDescription());
    }

    @Test
    public void deleteRideOfferTest() {
        RideOffer created = rideOfferInvoker.createOffer(new RideOffer("OfferToDelete", "Delete Me!"));
        RideOffer getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getId(), getCreated.getId());
        rideOfferInvoker.deleteOffer(created.getId());
        RideOffer deleted = rideOfferInvoker.getOffer(created.getId());
        assertEquals(null, deleted);
    }
}
