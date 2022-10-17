package com.spring.webtest.request;

import com.spring.webtest.controller.RideRequestController;
import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.service.RideRequestService;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

class RideRequestWebClientInvoker implements RideRequestInvoker {
    private final WebTestClient client;

    public RideRequestWebClientInvoker(WebTestClient client) {
        this.client = client;
    }

    static RideRequestWebClientInvoker remoteServer() {
        return new RideRequestWebClientInvoker(WebTestClient.bindToServer().baseUrl("http://localhost:8080").build());
    }

    static RideRequestWebClientInvoker mockServer(RideRequestService service) {
        System.out.println(service);
        return new RideRequestWebClientInvoker(WebTestClient.bindToController(new RideRequestController(service)).build());
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