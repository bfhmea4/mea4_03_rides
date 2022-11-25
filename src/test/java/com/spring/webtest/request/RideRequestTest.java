package com.spring.webtest.request;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import com.spring.webtest.WebTestApplication;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.RideRequestDto;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.RideRequestService;
import com.spring.webtest.service.UserService;
import com.spring.webtest.user.UserInvoker;
import com.spring.webtest.user.UserServiceInvoker;
import com.spring.webtest.user.UserWebClientInvoker;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class, RideRequestService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RideRequestTest {

    @Autowired
    private RideRequestService service;

    @Autowired
    private UserService userService;

    private RideRequestInvoker rideRequestInvoker;
    private UserInvoker userInvoker;


    @Value("${useRestMode:true}")
    private boolean useRestMode;

    private User testUser;
    private User wrongUser;

    @BeforeAll
    void setup() {
        if (useRestMode) {
            this.rideRequestInvoker = WebClientRideRequestInvoker.mockServer(service);
            userInvoker = UserWebClientInvoker.mockServer(userService);
        } else {
            this.rideRequestInvoker = new RideRequestServiceInvoker(service);
            userInvoker = new UserServiceInvoker(userService);
        }
        testUser = new User("Test", "User", "test@gmail.com", "Musteradresse", "thisIsAStrongPassword13.");
        wrongUser = new User("Wrong", "User", "wrong@gmail.com", "Musteradresse", "thisIsAnotherStrongPassword14.");

        UserDto testUserDto = userInvoker.createUser(testUser);
        testUser.setId(testUserDto.getId());
        UserDto wrongUserDto = userInvoker.createUser(wrongUser);
        wrongUser.setId(wrongUserDto.getId());


    }

    @Test
    void get_request_that_does_not_exist() {
        assertThat(rideRequestInvoker.getRequest(7777)).isNull();
    }

    @Test
    void get_request_by_id() throws OperationNotSupportedException, IllegalAccessException {
        RideRequestDto rideRequestDto = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
        RideRequestDto rideRequestDto1 = rideRequestInvoker.getRequest(rideRequestDto.getId());
        assertThat(rideRequestDto1).isNotNull();
        assertThat(rideRequestDto1.getId()).isEqualTo(rideRequestDto.getId());
        assertThat(rideRequestDto1.getDescription()).isEqualTo("Test");
    }

    @Test
    void creating_a_request_returns_the_new_request() throws OperationNotSupportedException, IllegalAccessException {
        RideRequestDto created = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
        assertThat(created).isNotNull();
        assertThat(created.getDescription()).isEqualTo("Test");
    }

    @Test
    void create_request_without_login() {
        RideRequestDto created = null;
        try {
            RideRequest rideRequest = new RideRequest("created", "Test", null);
            created = rideRequestInvoker.createRequest(rideRequest);
            assertThat(created).isNull();
        } catch (OperationNotSupportedException | IllegalAccessException e) {
            assertThat(created).isNull();
        }
    }

    @Test
    void create_request_with_different_user_data_than_DB() {
        RideRequestDto created = null;
        try {
            User alteredUser = new User(testUser.getId(), testUser.getFirstName(), "Another Name", testUser.getAddress(), testUser.getEmail(), testUser.getPassword());
            created = rideRequestInvoker.createRequest(new RideRequest("created", "Test", alteredUser));
            assertThat(created).isNull();
        } catch (OperationNotSupportedException | IllegalAccessException e) {
            assertThat(created).isNull();
        }

    }



    @Test
    void update_request() throws OperationNotSupportedException, IllegalAccessException {
        RideRequestDto rideRequest = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
        RideRequest rideRequestUpdated = new RideRequest(rideRequest.getId(), "created", "Updated Test", testUser);
        RideRequestDto rideRequest1 = rideRequestInvoker.updateRequest(rideRequestUpdated);
        assertThat(rideRequest1).isNotNull();
        assertThat(rideRequest1.getId()).isEqualTo(rideRequest.getId());
        assertThat(rideRequest1.getDescription()).isEqualTo(rideRequestUpdated.getDescription());
    }

    @Test
    void updating_a_request_by_other_user() throws OperationNotSupportedException, IllegalAccessException {
        RideRequestDto beforeUpdate = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
        RideRequestDto created = rideRequestInvoker.getRequest(beforeUpdate.getId());
        RideRequestDto updated = null;
        try {
            User alteredUser = new User(testUser.getId() + 1, testUser.getFirstName(), "Another Name", testUser.getAddress(), testUser.getEmail(), testUser.getPassword());
            updated = rideRequestInvoker.updateRequest(new RideRequest(beforeUpdate.getId(), "updated", "Test", alteredUser));
            created = rideRequestInvoker.getRequest(beforeUpdate.getId());
        } catch (IllegalAccessException e) {
            created = rideRequestInvoker.getRequest(beforeUpdate.getId());
        } finally {
            assertThat(updated).isNull();
            assertThat(created.getTitle()).isEqualTo(beforeUpdate.getTitle());
        }
    }

    @Test
    void updating_a_request_by_null_user() throws OperationNotSupportedException, IllegalAccessException {
        RideRequestDto beforeUpdate = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
        RideRequestDto created = rideRequestInvoker.getRequest(beforeUpdate.getId());
        RideRequestDto updated = null;
        try {
            updated = rideRequestInvoker.updateRequest(new RideRequest(beforeUpdate.getId(), "updated", "Test", null));
            created = rideRequestInvoker.getRequest(beforeUpdate.getId());
        } catch (IllegalAccessException e) {
            created = rideRequestInvoker.getRequest(beforeUpdate.getId());
        } finally {
            assertThat(updated).isNull();
            assertThat(created.getTitle()).isEqualTo(beforeUpdate.getTitle());
        }
    }

    @Test
    void getting_an_updated_request_returns_the_updated_request() throws OperationNotSupportedException, IllegalAccessException {
        RideRequestDto rideRequestDto = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
        RideRequestDto rideRequest1 = rideRequestInvoker.updateRequest(new RideRequest(rideRequestDto.getId(), "updated", "Another Test", testUser));
        RideRequestDto rideRequest2 = rideRequestInvoker.getRequest(rideRequest1.getId());
        assertThat(rideRequest2).isNotNull();
        assertThat(rideRequest2.getId()).isEqualTo(rideRequest1.getId());
        assertThat(rideRequest2.getDescription()).isEqualTo("Another Test");

    }

    @Test
    void delete_request() throws OperationNotSupportedException, IllegalAccessException {
        RideRequestDto rideRequestCreated = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
        System.out.println("ID of request to delete: " + rideRequestCreated.getId());
        assertThat(rideRequestCreated).isNotNull();
        rideRequestInvoker.deleteRequest(rideRequestCreated.getId());
        assertThat(rideRequestInvoker.getRequest(rideRequestCreated.getId())).isNull();
    }
//TODO activate tests after login is implemented correctly and id's can be compared

//    @Test
//    void deleting_a_request_by_wrong_user() throws OperationNotSupportedException, IllegalAccessException {
//        RideRequestDto created = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
//        RideRequestDto getCreated = null;
//        try {
//            rideRequestInvoker.deleteRequest(created.getId());
//            getCreated = rideRequestInvoker.getRequest(created.getId());
//        } catch (IllegalAccessException e) {
//            getCreated = rideRequestInvoker.getRequest(created.getId());
//        } finally {
//            assertThat(getCreated).isNotNull();
//            assertThat(getCreated.getId()).isEqualTo(created.getId());
//        }
//    }
//
//    @Test
//    void deleting_a_request_by_null_user() throws OperationNotSupportedException, IllegalAccessException {
//        RideRequestDto created = rideRequestInvoker.createRequest(new RideRequest("created", "Test", testUser));
//        RideRequestDto getCreated = null;
//        try {
//            rideRequestInvoker.deleteRequest(new RideRequest(created.getId(), created.getTitle(), created.getDescription(), null));
//            getCreated = rideRequestInvoker.getRequest(created.getId());
//        } catch (IllegalAccessException e) {
//            getCreated = rideRequestInvoker.getRequest(created.getId());
//        } finally {
//            assertThat(getCreated).isNotNull();
//            assertThat(getCreated.getId()).isEqualTo(created.getId());
//        }
//    }

    @AfterAll
    void cleanUp() {
        userInvoker.deleteUser(testUser.getId());
        userInvoker.deleteUser(wrongUser.getId());
    }
}