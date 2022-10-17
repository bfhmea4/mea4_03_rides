package com.spring.webtest.rideOffer;

import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.RideOfferService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RideOfferServiceTest {

    @Autowired
    private RideOfferService service;

    InvokeRideOfferServiceTest serviceTest = new InvokeRideOfferServiceTest();

    @Test
    public void testShouldReturnNull() throws Exception {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> serviceTest.getRequest(50));
        assertEquals("Find Offer by id: Ride offer with id: 50 not found", thrown.getMessage());
    }

    @Test
    public void createRideOfferTest() throws Exception {
        RideOffer rideOffer = new RideOffer("Test", "Test description");
        RideOffer created = serviceTest.createRequest(rideOffer);
        RideOffer getCreated = serviceTest.getRequest(created.getId());
        assertEquals(created.getTitle(), getCreated.getTitle());
    }

    @Test
    public void updateRideOfferTest() {
        RideOffer rideOffer = new RideOffer("Test", "Test description");
        RideOffer created = serviceTest.createRequest(rideOffer);
        RideOffer newRideOffer = new RideOffer(created.getId(), "updated", "Test description updated");
        serviceTest.updateRequest(newRideOffer);
        RideOffer updated = serviceTest.getRequest(newRideOffer.getId());
        assertEquals(updated.getId(), newRideOffer.getId());
        assertEquals(updated.getTitle(), "updated");
        assertEquals(updated.getDescription(), "Test description updated");
    }

    @Test
    public void deleteRideOfferTest() {
        RideOffer rideOffer = new RideOffer("Test", "Test description");
        RideOffer created = serviceTest.createRequest(rideOffer);
        RideOffer getCreated = serviceTest.getRequest(created.getId());
        assertEquals(created.getTitle(), getCreated.getTitle());
        serviceTest.deleteRequest(created.getId());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                () -> serviceTest.getRequest(created.getId()));
        assertEquals("Find Offer by id: Ride offer with id: " + created.getId() +
                " not found", thrown.getMessage());
    }


    class InvokeRideOfferServiceTest implements RideOfferInvoker {

        @Override
        public RideOffer createRequest(RideOffer rideOffer) {
            return service.addRideOffer(rideOffer);
        }

        @Override
        public RideOffer getRequest(long id) {
            return service.findRideOfferById(id);
        }

        @Override
        public RideOffer updateRequest(RideOffer rideOffer) {
            return service.updateRiderOffer(rideOffer);
        }

        @Override
        public void deleteRequest(long id) {
            service.deleteRideOfferById(id);

        }
    }


}
