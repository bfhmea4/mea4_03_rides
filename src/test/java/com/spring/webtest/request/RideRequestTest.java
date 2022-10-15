package com.spring.webtest.request;

import com.spring.webtest.controller.RideRequestController;
import com.spring.webtest.database.entities.RideRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

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

        static WebClientRideRequestInvoker mockServer() {
            return new WebClientRideRequestInvoker(WebTestClient.bindToController(new RideRequestController()).build());
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
        return null;
    }

    @Override
    public void deleteRequest(long id) {

    }
}

class RideRequestTest {

    private final RideRequestInvoker rideRequestInvoker = WebClientRideRequestInvoker.mockServer();

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