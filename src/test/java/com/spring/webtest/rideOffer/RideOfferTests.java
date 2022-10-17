package com.spring.webtest.rideOffer;

import com.spring.webtest.WebTestApplication;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.RideOfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class, RideOfferService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RideOfferTests {

    @Autowired
    private RideOfferService service;

    private RideOfferInvoker rideOfferInvoker;

    @BeforeAll
    void setup() {
        if (true)
            System.out.println("evaluated true");
            this.rideOfferInvoker = WebClientRideOfferInvoker.mockServer(service);
        if (false)
            this.rideOfferInvoker = new ServiceRideOfferInvoker(service);
    }

    @Test
    public void testShouldReturnNull() {
        assertEquals(rideOfferInvoker.getOffer(14654645), null);
    }

    @Test
    public void createRideOfferTest() {
        RideOffer rideOffer = new RideOffer("Test", "Test description");
        RideOffer created = rideOfferInvoker.createOffer(rideOffer);
        RideOffer getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getTitle(), getCreated.getTitle());
    }

    @Test
    public void updateRideOfferTest() {
        RideOffer rideOffer = new RideOffer("Test", "Test description");
        RideOffer created = rideOfferInvoker.createOffer(rideOffer);
        RideOffer newRideOffer = new RideOffer(created.getId(), "updated", "Test description updated");
        rideOfferInvoker.updateOffer(newRideOffer);
        RideOffer updated = rideOfferInvoker.getOffer(newRideOffer.getId());
        assertEquals(updated.getId(), newRideOffer.getId());
        assertEquals(updated.getTitle(), "updated");
        assertEquals(updated.getDescription(), "Test description updated");
    }

    @Test
    public void deleteRideOfferTest() {
        RideOffer rideOffer = new RideOffer("Test", "Test description");
        RideOffer created = rideOfferInvoker.createOffer(rideOffer);
        RideOffer getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getTitle(), getCreated.getTitle());
        rideOfferInvoker.deleteOffer(created.getId());
        assertEquals(rideOfferInvoker.getOffer(created.getId()), null);
    }
}
