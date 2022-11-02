package com.spring.webtest.rideOffer;

import com.spring.webtest.WebTestApplication;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.RideOfferDto;
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
        User user = new User(1,"J.", "Lanz", "j.lanz@gmail.com", "Musteradresse", "1234");
        RideOffer rideOffer = new RideOffer("Create Test", "I'm new!", user);
        RideOfferDto created = rideOfferInvoker.createOffer(rideOffer);
        RideOfferDto getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getTitle(), getCreated.getTitle());
    }

    @Test
    public void updateRideOfferTest() {
        User user = new User(1,"J.", "Lanz", "j.lanz@gmail.com", "Musteradresse", "1234");
        RideOffer rideOffer = new RideOffer("OfferToUpdate", "Need Update", user);
        RideOfferDto created = rideOfferInvoker.createOffer(rideOffer);
        System.out.println(created.toString());
        RideOffer rideOfferToUpdate = new RideOffer(created.getId(), "UpdatedOffer", "Updated!", user);
        RideOfferDto updatedRideOffer = rideOfferInvoker.updateOffer(rideOfferToUpdate);
        RideOfferDto getUpdatedRideOffer = rideOfferInvoker.getOffer(updatedRideOffer.getId());
        assertEquals(getUpdatedRideOffer.getId(), updatedRideOffer.getId());
        assertEquals("UpdatedOffer", getUpdatedRideOffer.getTitle());
        assertEquals("Updated!",getUpdatedRideOffer.getDescription());
    }

    @Test
    public void deleteRideOfferTest() {
        User user = new User(1,"J.", "Lanz", "j.lanz@gmail.com", "Musteradresse", "1234");
        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("OfferToDelete", "Delete Me!", user));
        RideOfferDto getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getId(), getCreated.getId());
        rideOfferInvoker.deleteOffer(created.getId());
        RideOfferDto deleted = rideOfferInvoker.getOffer(created.getId());
        assertEquals(null, deleted);
    }
}
