package com.spring.webtest.rideOffer;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.database.entities.Address;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.exception.RideOfferNotFoundException;
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
    @WithMockUser(username = "myemail@me.com")
    public void getting_a_offer_that_does_not_exist() {
        assertThrows(RideOfferNotFoundException.class, () -> service.findRideOfferById(1000L));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_a_offer_without_saved_user_throws_exception() throws JoseException, OperationNotSupportedException, IllegalAccessException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        LocalDateTime testTime = LocalDateTime.of(2023, Month.JANUARY, 11, 11,11);
        Address testFromAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        Address testToAddress = new Address(2L, "Zwistrasse", "22", 3622L, "Thun");
        RideOffer testOffer = new RideOffer(1L, "From Bern to Thun", "Ride from Bern to Thun", testTime, testUser, testFromAddress, testToAddress);

        assertThrows(JpaObjectRetrievalFailureException.class, () -> this.service.addRideOffer(testOffer));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    public void getting_a_offer_by_id_returns_offer() throws JoseException, OperationNotSupportedException, IllegalAccessException {
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


//
////    @Test
////    void get_offer_by_id() throws OperationNotSupportedException, IllegalAccessException {
////        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("created", "Test", testUser));
////        RideOfferDto getCreated = rideOfferInvoker.getOffer(created.getId());
////        assertThat(getCreated).isNotNull();
////        assertThat(getCreated.getId()).isEqualTo(created.getId());
////        assertThat(getCreated.getDescription()).isEqualTo("Test");
////    }
////
////    @Test
////    public void creating_a_offer_returns_the_new_offer() throws OperationNotSupportedException, IllegalAccessException {
////        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("created", "Test", testUser));
////        assertThat(created).isNotNull();
////        assertThat(created.getDescription()).isEqualTo("Test");
////    }
////
////    @Test
////    void create_offer_without_login() {
////        RideOfferDto created = null;
////        try {
////            RideOffer rideOffer = new RideOffer("created", "Test", null);
////            created = rideOfferInvoker.createOffer(rideOffer);
////            assertThat(created).isNull();
////        } catch (OperationNotSupportedException | IllegalAccessException e) {
////            assertThat(created).isNull();
////        }
////    }
////
////    @Test
////    void create_offer_with_different_user_data_than_DB() {
////        RideOfferDto created = null;
////        try {
////            User alteredUser = new User(testUser.getId() + 1, testUser.getFirstName(), "Another Name", testUser.getAddress(), testUser.getEmail(), testUser.getPassword());
////            created = rideOfferInvoker.createOffer(new RideOffer("created", "Test", alteredUser));
////            assertThat(created).isNull();
////        } catch (OperationNotSupportedException | IllegalAccessException e) {
////            assertThat(created).isNull();
////        }
////    }
////
////    @Test
////    public void updateRideOfferTest() throws IllegalAccessException, OperationNotSupportedException {
////        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("OfferToUpdate", "Need Update", testUser));
////        RideOfferDto updatedRideOffer = rideOfferInvoker.updateOffer(new RideOffer(created.getId(), "UpdatedOffer", "Updated!", testUser));
////        RideOfferDto getUpdatedRideOffer = rideOfferInvoker.getOffer(updatedRideOffer.getId());
////        assertEquals(getUpdatedRideOffer.getId(), updatedRideOffer.getId());
////        assertEquals("UpdatedOffer", getUpdatedRideOffer.getTitle());
////        assertEquals("Updated!", getUpdatedRideOffer.getDescription());
////    }
////
////    @Test
////    void updating_a_offer_by_other_user() throws OperationNotSupportedException, IllegalAccessException {
////        RideOfferDto beforeUpdate = rideOfferInvoker.createOffer(new RideOffer("created", "Test", testUser));
////        RideOfferDto created = rideOfferInvoker.getOffer(beforeUpdate.getId());
////        RideOfferDto updated = null;
////        try {
////            User alteredUser = new User(testUser.getId() + 1, testUser.getFirstName(), "Another Name", testUser.getAddress(), testUser.getEmail(), testUser.getPassword());
////            updated = rideOfferInvoker.updateOffer(new RideOffer(beforeUpdate.getId(), "updated", "Test", alteredUser));
////            created = rideOfferInvoker.getOffer(beforeUpdate.getId());
////        } catch (IllegalAccessException e) {
////            created = rideOfferInvoker.getOffer(beforeUpdate.getId());
////        } finally {
////            assertThat(updated).isNull();
////            assertThat(created.getTitle()).isEqualTo(beforeUpdate.getTitle());
////        }
////    }
////
////    @Test
////    void update_offer() throws OperationNotSupportedException, IllegalAccessException {
////        RideOfferDto rideOffer = rideOfferInvoker.createOffer(new RideOffer("created", "Test", testUser));
////        RideOffer rideOfferUpdated = new RideOffer(rideOffer.getId(), "created", "Updated Test", testUser);
////        RideOfferDto rideRequest1 = rideOfferInvoker.updateOffer(rideOfferUpdated);
////        assertThat(rideRequest1).isNotNull();
////        assertThat(rideRequest1.getId()).isEqualTo(rideOffer.getId());
////        assertThat(rideRequest1.getDescription()).isEqualTo(rideOfferUpdated.getDescription());
////    }
////
////    @Test
////    void updating_a_offer_by_null_user() throws OperationNotSupportedException, IllegalAccessException {
////        RideOfferDto beforeUpdate = rideOfferInvoker.createOffer(new RideOffer("created", "Test", testUser));
////        RideOfferDto created = rideOfferInvoker.getOffer(beforeUpdate.getId());
////        RideOfferDto updated = null;
////        try {
////            updated = rideOfferInvoker.updateOffer(new RideOffer(beforeUpdate.getId(), "updated", "Test", null));
////            created = rideOfferInvoker.getOffer(beforeUpdate.getId());
////        } catch (IllegalAccessException e) {
////            created = rideOfferInvoker.getOffer(beforeUpdate.getId());
////        } finally {
////            assertThat(updated).isNull();
////            assertThat(created.getTitle()).isEqualTo(beforeUpdate.getTitle());
////        }
////    }
////
////    @Test
////    public void delete_offer() throws IllegalAccessException, OperationNotSupportedException {
////        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("OfferToDelete", "Delete Me!", testUser));
////        RideOfferDto getCreated = rideOfferInvoker.getOffer(created.getId());
////        assertThat(getCreated).isNotNull();
////        rideOfferInvoker.deleteOffer(created.getId());
////        assertThat(rideOfferInvoker.getOffer(created.getId())).isNull();
////    }
//
//    //TODO activate tests after login is implemented correctly and id's can be compared
//
//
//    //    @Test
////    void deleting_a_request_by_wrong_user() throws OperationNotSupportedException, IllegalAccessException {
////        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("created", "Test", testUser));
////        RideOfferDto getCreated = null;
////        try {
////            login with wrong user
////            rideOfferInvoker.deleteOffer(created.getId());
////            getCreated = rideOfferInvoker.getOffer(created.getId());
////        } catch (IllegalAccessException e) {
////            getCreated = rideOfferInvoker.getOffer(created.getId());
////        } finally {
////            assertThat(getCreated).isNotNull();
////            assertThat(getCreated.getId()).isEqualTo(created.getId());
////        }
////    }
////
////    @Test
////    void deleting_a_request_by_null_user() throws OperationNotSupportedException, IllegalAccessException {
////        RideOfferDto created = rideOfferInvoker.createOffer(new RideOffer("created", "Test", testUser));
////        RideOfferDto getCreated = null;
////        try {
////            rideOfferInvoker.deleteOffer(created.getId());
////            getCreated = rideOfferInvoker.getOffer(created.getId());
////        } catch (IllegalAccessException e) {
////            getCreated = rideOfferInvoker.getOffer(created.getId());
////        } finally {
////            assertThat(getCreated).isNotNull();
////            assertThat(getCreated.getId()).isEqualTo(created.getId());
////        }
////    }
}
