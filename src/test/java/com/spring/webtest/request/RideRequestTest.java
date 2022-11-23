package com.spring.webtest.request;

import com.spring.webtest.WebTestApplication;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.RideRequestDto;
import com.spring.webtest.service.RideRequestService;
import com.spring.webtest.service.UserService;
import com.spring.webtest.user.UserInvoker;
import com.spring.webtest.user.UserServiceInvoker;
import com.spring.webtest.user.UserWebClientInvoker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={WebTestApplication.class, RideRequestService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RideRequestTest {

    @Autowired
    private RideRequestService service;

    @Autowired
    private UserService userService;

    private RideRequestInvoker rideRequestInvoker;

    @Value("${useRestMode:false}")
    private boolean useRestMode;

    private User testUser;

    @BeforeAll
    void setup() {
        UserInvoker userInvoker;
        if (useRestMode) {
            this.rideRequestInvoker = WebClientRideRequestInvoker.mockServer(service);
            userInvoker = UserWebClientInvoker.mockServer(userService);
        } else {
            this.rideRequestInvoker = new RideRequestServiceInvoker(service);
            userInvoker = new UserServiceInvoker(userService);
        }
        testUser = new User(1,"J.", "Lanz", "j.lanz@gmail.com", "Musteradresse", "thisIsAStrongPassword13.");
        userInvoker.createUser(testUser);
    }

    @Test
    void getting_a_request_that_does_not_exist_returns_null() {
        assertThat(rideRequestInvoker.getRequest(7777)).isNull();
    }

    @Test
    void creating_a_request_returns_the_new_request() {
        RideRequest rideRequest = new RideRequest("created", "Test", testUser);
        RideRequestDto rideRequestDto = rideRequestInvoker.createRequest(rideRequest);
        assertThat(rideRequestDto).isNotNull();
        assertThat(rideRequestDto.getDescription()).isEqualTo("Test");
    }

    @Test
    void getting_a_request_by_id_returns_the_request() {
        RideRequest rideRequest = new RideRequest("created", "Test", testUser);
        RideRequestDto rideRequestDto = rideRequestInvoker.createRequest(rideRequest);
        RideRequestDto rideRequestDto1 = rideRequestInvoker.getRequest(rideRequestDto.getId());
        assertThat(rideRequestDto1).isNotNull();
        assertThat(rideRequestDto1.getId()).isEqualTo(rideRequestDto.getId());
        assertThat(rideRequestDto1.getDescription()).isEqualTo("Test");
    }

    @Test
    void updating_a_request_returns_the_updated_request() {
        RideRequest rideRequestCreated = new RideRequest("created", "Test", testUser);
        RideRequestDto rideRequest = rideRequestInvoker.createRequest(rideRequestCreated);
        RideRequest rideRequestUpdated = new RideRequest(rideRequest.getId(), "created", "Another Test", testUser);
        RideRequestDto rideRequest1 = rideRequestInvoker.updateRequest(rideRequestUpdated);
        assertThat(rideRequest1).isNotNull();
        assertThat(rideRequest1.getId()).isEqualTo(rideRequest.getId());
        assertThat(rideRequest1.getDescription()).isEqualTo("Another Test");
    }

    @Test
    void getting_an_updated_request_returns_the_updated_request() {
        RideRequest rideRequestCreated = new RideRequest("created", "Test", testUser);
        RideRequestDto rideRequestDto = rideRequestInvoker.createRequest(rideRequestCreated);
        RideRequest rideRequestUpdated = new RideRequest(rideRequestDto.getId(), "updated", "Another Test", testUser);
        RideRequestDto rideRequest1 = rideRequestInvoker.updateRequest(rideRequestUpdated);
        RideRequestDto rideRequest2 = rideRequestInvoker.getRequest(rideRequest1.getId());
        assertThat(rideRequest2).isNotNull();
        assertThat(rideRequest2.getId()).isEqualTo(rideRequest1.getId());
        assertThat(rideRequest2.getDescription()).isEqualTo("Another Test");

    }

    @Test
    void deleting_a_request_removes_the_request() {
        RideRequest rideRequestCreated = new RideRequest("created", "Test", testUser);
        RideRequestDto rideRequest = rideRequestInvoker.createRequest(rideRequestCreated);
        rideRequestInvoker.deleteRequest(rideRequest.getId());
        RideRequestDto rideRequest1 = rideRequestInvoker.getRequest(rideRequest.getId());
        assertThat(rideRequest1).isNull();
    }
}