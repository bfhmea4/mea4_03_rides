package com.spring.webtest.rideOffer;

import com.spring.webtest.WebTestApplication;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.RideOfferDto;
import com.spring.webtest.service.RideOfferService;
import com.spring.webtest.service.UserService;
import com.spring.webtest.user.UserInvoker;
import com.spring.webtest.user.UserServiceInvoker;
import com.spring.webtest.user.UserWebClientInvoker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(classes = {WebTestApplication.class, RideOfferService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RideOfferTests {
    @Value("${useRestMode:true}")
    private boolean useRestMode;

    @Autowired
    private RideOfferService service;

    @Autowired
    private UserService userService;

    private RideOfferInvoker rideOfferInvoker;

    private User testUser;

    @BeforeAll
    void setup() {
        UserInvoker userInvoker;
        if (useRestMode) {
            this.rideOfferInvoker = WebClientRideOfferInvoker.mockServer(service);
            userInvoker = UserWebClientInvoker.mockServer(userService);
        } else {
            this.rideOfferInvoker = new ServiceRideOfferInvoker(service);
            userInvoker = new UserServiceInvoker(userService);
        }
        testUser = new User(1,"J.", "Lanz", "j.lanz@gmail.com", "Musteradresse", "thisIsAStrongPassword13.");
        userInvoker.createUser(testUser);
    }

    @Test
    public void testShouldReturnNull() {
        assertNull(rideOfferInvoker.getOffer(5465465));
    }

    @Test
    public void createRideOfferTest() {
        RideOffer rideOffer = new RideOffer("Create Test", "I'm new!", testUser);
        RideOfferDto created = rideOfferInvoker.createOffer(rideOffer);
        RideOfferDto getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getTitle(), getCreated.getTitle());
    }

    @Test
    public void updateRideOfferTest() {
        RideOffer rideOffer = new RideOffer("OfferToUpdate", "Need Update", testUser);
        RideOfferDto created = rideOfferInvoker.createOffer(rideOffer);
        System.out.println(created.toString());
        RideOffer rideOfferToUpdate = new RideOffer(created.getId(), "UpdatedOffer", "Updated!", testUser);
        RideOfferDto updatedRideOffer = rideOfferInvoker.updateOffer(rideOfferToUpdate);
        RideOfferDto getUpdatedRideOffer = rideOfferInvoker.getOffer(updatedRideOffer.getId());
        assertEquals(getUpdatedRideOffer.getId(), updatedRideOffer.getId());
        assertEquals("UpdatedOffer", getUpdatedRideOffer.getTitle());
        assertEquals("Updated!",getUpdatedRideOffer.getDescription());
    }

    @Test
    public void deleteRideOfferTest() {
        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("OfferToDelete", "Delete Me!", testUser));
        RideOfferDto getCreated = rideOfferInvoker.getOffer(created.getId());
        assertEquals(created.getId(), getCreated.getId());
        rideOfferInvoker.deleteOffer(created.getId());
        RideOfferDto deleted = rideOfferInvoker.getOffer(created.getId());
        assertNull(deleted);
    }
}
