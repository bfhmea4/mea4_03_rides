package com.spring.webtest.rideOffer;


import com.spring.webtest.controller.RideOfferController;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.dto.RideOfferDto;
import com.spring.webtest.service.RideOfferService;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;



class WebClientRideOfferInvoker implements RideOfferInvoker {

    private final WebTestClient client;

    public WebClientRideOfferInvoker(WebTestClient client) {
        this.client = client;
    }

    static WebClientRideOfferInvoker remoteServer() {
        return new WebClientRideOfferInvoker(WebTestClient.bindToServer().baseUrl("http://localhost:8080").build());
    }

    static WebClientRideOfferInvoker mockServer(RideOfferService service) {
        return new WebClientRideOfferInvoker(WebTestClient.bindToController(new RideOfferController(service)).build());
    }


    @Override
    public RideOfferDto createOffer(RideOffer rideOffer) {
        return client.post()
                .uri("/api/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(rideOffer), RideOffer.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideOfferDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public RideOfferDto getOffer(long id) {
        return client.get()
                .uri("/api/offer/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideOfferDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public RideOfferDto updateOffer(RideOffer rideOffer) {

        return client.put()
                .uri("/api/offer/" + rideOffer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(rideOffer), RideOffer.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(RideOfferDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public void deleteOffer(long id) {
        client.delete()
                .uri("/api/offer/" + id)
                .exchange();

    }

}




