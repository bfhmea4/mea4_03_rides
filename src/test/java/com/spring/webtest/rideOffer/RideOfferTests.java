package com.spring.webtest.rideOffer;

//import com.spring.webtest.TestApplication;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.RideOfferNotFoundException;
import com.spring.webtest.service.RideOfferService;
import com.spring.webtest.user.UserInvoker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


//@SpringBootTest(classes = {TestApplication.class})
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RideOfferTests {

    @Autowired
    private RideOfferService rideOfferService;

    @BeforeAll
    public void init() {
        System.out.println("Initializing RideOfferTests");
    }

    @Test
    void getting_a_ride_offer_that_does_not_exist_throws_exception() {
        assertThrows(
                RideOfferNotFoundException.class, () -> rideOfferService.findRideOfferById(1000L)
        );
    }
//
//    @Autowired
//    @Qualifier("rideOfferInvoker")
//    private RideOfferInvoker rideOfferInvoker;
//
//    @Autowired
//    @Qualifier("userInvoker")
//    private UserInvoker userInvoker;
//
//    @BeforeEach
//    void setup() {
//        User testUser = new User("Test", "User", "test@gmail.com", "Musteradresse", "thisIsAStrongPassword13.");
//        User wrongUser = new User("Wrong", "User", "wrong@gmail.com", "Musteradresse", "thisIsAnotherStrongPassword14.");
//
//        UserDto testUserDto = userInvoker.createUser(testUser);
//        testUser.setId(testUserDto.getId());
//        UserDto wrongUserDto = userInvoker.createUser(wrongUser);
//        wrongUser.setId(wrongUserDto.getId());
//
//    }
//
//    @Test
//    public void getting_a_offer_that_does_not_exist() {
//        assertThat(rideOfferInvoker.getOffer(1000)).isNull();
//    }
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
