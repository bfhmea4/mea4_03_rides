package com.spring.webtest.rideRequest;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.exception.RideRequestNotFoundException;
import com.spring.webtest.exception.UnauthenticatedException;
import com.spring.webtest.exception.UnauthorizedException;
import com.spring.webtest.service.RideRequestService;
import com.spring.webtest.service.UserService;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RideRequestServiceTests {


    @Autowired
    private RideRequestService service;

    @Autowired
    private UserService userService;

    @BeforeAll
    public void init() {
        System.out.println("Initializing UserTests");
    }


    @Test
    public void getting_an_request_when_not_authorized_throws_exception() {
        assertThrows(UnauthenticatedException.class, () -> this.service.findRideRequestById(1L));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_an_request_that_does_not_exist() {
        assertThrows(RideRequestNotFoundException.class, () -> this.service.findRideRequestById(1000L));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_an_Request_without_saved_user_throws_exception() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideRequest testRequest = new RideRequest(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);

        assertThrows(JpaObjectRetrievalFailureException.class, () -> this.service.addRideRequest(testRequest));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_an_Request_by_id_returns_Request() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        userService.save(testUser);
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideRequest testRequest = new RideRequest(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);
        this.service.addRideRequest(testRequest);

        RideRequest Request = this.service.findRideRequestById(1L);
        assertEquals(testRequest, Request);
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void updating_an_Request_returns_updated_Request() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        userService.save(testUser);
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideRequest testRequest = new RideRequest(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);
        this.service.addRideRequest(testRequest);

        testRequest.setDescription("From Bern to Oberland");
        RideRequest Request = this.service.updateRideRequest(testRequest);

        assertEquals("From Bern to Oberland", Request.getDescription());
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void deleting_an_Request_and_get_it_throws_exception() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        userService.save(testUser);
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideRequest testRequest = new RideRequest(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);
        this.service.addRideRequest(testRequest);

        this.service.deleteRideRequest(1L);

        assertThrows(RideRequestNotFoundException.class, () -> this.service.findRideRequestById(1L));
    }
}