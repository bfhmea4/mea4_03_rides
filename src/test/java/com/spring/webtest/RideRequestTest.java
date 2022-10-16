package com.spring.webtest;

import com.spring.webtest.controller.RideRequestController;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.service.RideRequestService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import javax.persistence.Access;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

interface RideRequestInvoker {
    RideRequest createRequest(String text);
    RideRequest getRequest(long id);
    RideRequest updateRequest(long id, String text);
    void deleteRequest(long id);
}

class WebClientRideRequestInvoker implements RideRequestInvoker {
    private final WebTestClient client;

    public WebClientRideRequestInvoker(WebTestClient client) {
        this.client = client;
    }

    static WebClientRideRequestInvoker remoteServer() {
        return new WebClientRideRequestInvoker(WebTestClient.bindToServer().baseUrl("http://localhost:8080").build());
    }

    static WebClientRideRequestInvoker mockServer(RideRequestService service) {
        System.out.println(service);
        return new WebClientRideRequestInvoker(WebTestClient.bindToController(new RideRequestController(service)).build());
    }

    @Override
    public RideRequest createRequest(String text) {
        RideRequest rideRequest = new RideRequest(text);
        return client.post()
                .uri("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(rideRequest), RideRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideRequest.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public RideRequest getRequest(long id) {
        return client.get()
                .uri("/api/requests/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideRequest.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public RideRequest updateRequest(long id, String text) {
        RideRequest rideRequest = new RideRequest(id, text);
        return client.put()
                .uri("/api/requests/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(rideRequest), RideRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideRequest.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public void deleteRequest(long id) {
        client.delete()
                .uri("/api/requests/" + id)
                .exchange();
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest(classes={WebTestApplication.class, RideRequestService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RideRequestTest {

    @Autowired
    private RideRequestService service;

    private RideRequestInvoker rideRequestInvoker;

    @BeforeAll
    void setup() {
        this.rideRequestInvoker = WebClientRideRequestInvoker.mockServer(service);
    }

    @Test
    void getting_a_request_that_does_not_exist_returns_null() {
        assertThat(rideRequestInvoker.getRequest(27)).isNull();
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