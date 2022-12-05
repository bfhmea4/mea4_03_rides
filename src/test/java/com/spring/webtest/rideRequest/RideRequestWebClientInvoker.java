package com.spring.webtest.rideRequest;

import com.spring.webtest.database.entities.RideRequest;
import com.spring.webtest.dto.RideRequestDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class RideRequestWebClientInvoker implements RideRequestInvoker {
    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    @Override
    public RideRequestDto createRequest(RideRequest rideRequest) {
        return client.post()
                .uri("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(rideRequest), RideRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideRequestDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public RideRequestDto getRequest(long id) {
        return client.get()
                .uri("/api/requests/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideRequestDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public RideRequestDto updateRequest(RideRequest rideRequest) {
        return client.put()
                .uri("/api/requests/" + rideRequest.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(rideRequest), RideRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideRequestDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public void deleteRequest(long id) {
        client.delete()
                .uri("/api/requests/" + id)
                .exchange();
    }
}