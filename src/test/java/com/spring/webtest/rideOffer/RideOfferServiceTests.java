package com.spring.webtest.rideOffer;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.exception.RideOfferNotFoundException;
import com.spring.webtest.exception.UnauthenticatedException;
import com.spring.webtest.exception.UnauthorizedException;
import com.spring.webtest.service.AddressService;
import com.spring.webtest.service.RideOfferService;
import com.spring.webtest.service.UserService;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RideOfferServiceTests {

    @Autowired
    private RideOfferService service;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @BeforeAll
    public void init() {
        System.out.println("Initializing UserTests");
    }


    @Test
    public void getting_an_offer_when_not_authorized_throws_exception() {
        assertThrows(UnauthenticatedException.class, () -> this.service.findRideOfferById(1L));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_an_offer_that_does_not_exist() {
        assertThrows(RideOfferNotFoundException.class, () -> this.service.findRideOfferById(1000L));
    }

    @Test
    @WithMockUser(username = "notmyemail@me.com")
    public void saving_an_offer_when_authenticated_with_wrong_user_throws_exception() throws JoseException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        userService.save(testUser);
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideOffer testOffer = new RideOffer(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);
        assertThrows(UnauthorizedException.class, () -> this.service.addRideOffer(testOffer));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_an_offer_without_saved_user_throws_exception() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideOffer testOffer = new RideOffer(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);

        assertThrows(JpaObjectRetrievalFailureException.class, () -> this.service.addRideOffer(testOffer));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_an_offer_by_id_returns_offer() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        userService.save(testUser);
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideOffer testOffer = new RideOffer(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);
        this.service.addRideOffer(testOffer);

        RideOffer offer = this.service.findRideOfferById(1L);
        assertEquals(testOffer, offer);
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void updating_an_offer_returns_updated_offer() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        userService.save(testUser);
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideOffer testOffer = new RideOffer(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);
        this.service.addRideOffer(testOffer);

        testOffer.setDescription("From Bern to Oberland");
        RideOffer offer = this.service.updateRiderOffer(testOffer);

        assertEquals("From Bern to Oberland", offer.getDescription());
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void deleting_an_offer_and_get_it_throws_exception() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        userService.save(testUser);
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideOffer testOffer = new RideOffer(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);
        this.service.addRideOffer(testOffer);

        this.service.deleteRideOffer(1L);

        assertThrows(RideOfferNotFoundException.class, () -> this.service.findRideOfferById(1L));
    }


}
