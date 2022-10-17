package com.spring.webtest.request;

import com.spring.webtest.WebTestApplication;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.service.RideRequestService;
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

    private RideRequestInvoker rideRequestInvoker;

    @Value("${useRestMode}")
    private boolean useRestMode;

    @BeforeAll
    void setup() {
        if (useRestMode) {
            this.rideRequestInvoker = RideRequestWebClientInvoker.remoteServer();
        } else {
            this.rideRequestInvoker = new RideRequestServiceInvoker(service);
        }
    }

    @Test
    void getting_a_request_that_does_not_exist_returns_null() {
        assertThat(rideRequestInvoker.getRequest(7777)).isNull();
    }

    @Test
    void creating_a_request_returns_the_new_request() {
        RideRequest rideRequest = rideRequestInvoker.createRequest("Test");
        assertThat(rideRequest).isNotNull();
        assertThat(rideRequest.getContent()).isEqualTo("Test");
    }

    @Test
    void getting_a_request_by_id_returns_the_request() {
        RideRequest rideRequest = rideRequestInvoker.createRequest("Test");
        RideRequest rideRequest1 = rideRequestInvoker.getRequest(rideRequest.getId());
        assertThat(rideRequest1).isNotNull();
        assertThat(rideRequest1.getId()).isEqualTo(rideRequest.getId());
        assertThat(rideRequest1.getContent()).isEqualTo("Test");
    }

    @Test
    void updating_a_request_returns_the_updated_request() {
        RideRequest rideRequest = rideRequestInvoker.createRequest("Test");
        RideRequest rideRequest1 = rideRequestInvoker.updateRequest(rideRequest.getId(), "Another Test");
        assertThat(rideRequest1).isNotNull();
        assertThat(rideRequest1.getId()).isEqualTo(rideRequest.getId());
        assertThat(rideRequest1.getContent()).isEqualTo("Another Test");
    }

    @Test
    void getting_an_updated_request_returns_the_updated_request() {
        RideRequest rideRequest = rideRequestInvoker.createRequest("Test");
        RideRequest rideRequest1 = rideRequestInvoker.updateRequest(rideRequest.getId(), "Another Test");
        RideRequest rideRequest2 = rideRequestInvoker.getRequest(rideRequest1.getId());
        assertThat(rideRequest2).isNotNull();
        assertThat(rideRequest2.getId()).isEqualTo(rideRequest.getId());
        assertThat(rideRequest2.getContent()).isEqualTo("Another Test");

    }

    @Test
    void deleting_a_request_removes_the_request() {
        RideRequest rideRequest = rideRequestInvoker.createRequest("Test");
        rideRequestInvoker.deleteRequest(rideRequest.getId());
        RideRequest rideRequest1 = rideRequestInvoker.getRequest(rideRequest.getId());
        assertThat(rideRequest1).isNull();
    }
}