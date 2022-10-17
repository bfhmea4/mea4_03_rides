package com.spring.webtest.rideOffer;


import com.spring.webtest.controller.RideOfferController;
import com.spring.webtest.database.entities.RideOffer;
import com.spring.webtest.database.repositories.RideOfferRepository;
import com.spring.webtest.service.RideOfferService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class RideOfferTest {
    static class WebClientRideOfferInvoker implements RideOfferInvoker {
        private final WebTestClient client;

        private static RideOfferService service = Mockito.mock(RideOfferService.class);

        public WebClientRideOfferInvoker(WebTestClient client) {
            this.client = client;
        }

        static WebClientRideOfferInvoker remoteServer() {
            return new WebClientRideOfferInvoker(WebTestClient.bindToServer().baseUrl("http://localhost:8080").build());
        }

        static WebClientRideOfferInvoker mockServer() {
            return new WebClientRideOfferInvoker(WebTestClient.bindToController(new RideOfferController(service)).build());
        }


        @Override
        public RideOffer createRequest(RideOffer rideOffer) {
            RideOffer testOffer = rideOffer;
            return client.post()
                    .uri("/ride-offer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(testOffer), RideOffer.class)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .returnResult(RideOffer.class)
                    .getResponseBody().blockFirst();
        }

        @Override
        public RideOffer getRequest(long id) {
            return client.get()
                    .uri("/ride-offer/" + id)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .returnResult(RideOffer.class)
                    .getResponseBody().blockFirst();
        }

        @Override
        public RideOffer updateRequest(RideOffer rideOffer) {

            return client.put()
                    .uri("/ride-offer/" + rideOffer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(rideOffer), RideOffer.class)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .returnResult(RideOffer.class)
                    .getResponseBody().blockFirst();
        }

        @Override
        public void deleteRequest(long id) {
            client.delete()
                    .uri("/ride-offer/" + id);

        }
    }



    private final RideOfferInvoker rideOfferInvoker = WebClientRideOfferInvoker.mockServer();

    @Test
    void get_null_if_offer_does_not_exist() {
        assertThat(rideOfferInvoker.getRequest(50)).isNull();
    }

//    @Test
//    void get_returns_init_tuple_with_id_1() {
//        RideOffer test = new RideOffer("test", "test description");
//        rideOfferInvoker.createRequest(test);
//        RideOffer test2 = rideOfferInvoker.getRequest(1);
//        AssertionsForClassTypes.assertThat(test2).isNotNull();
//        AssertionsForClassTypes.assertThat(test2.getTitle()).isEqualTo("test");
//        AssertionsForClassTypes.assertThat(test2.getDescription()).isEqualTo("test description");
//    }

    @Test
    void creating_a_request_returns_the_new_request() {
        RideOffer test = new RideOffer("test", "test description");
        RideOffer created = rideOfferInvoker.createRequest(test);
        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("test");
        assertThat(created.getDescription()).isEqualTo("test description");
    }

    @Test
    void update_request_return_updated_request() {
        RideOffer test = new RideOffer(2, "test2", "test description 2");
        rideOfferInvoker.createRequest(test);
        RideOffer test2 = new RideOffer(2, "updatedTest", "test description updated");
        RideOffer updatedOffer = rideOfferInvoker.updateRequest(test2);
        assertThat(updatedOffer).isNotNull();
        assertThat(updatedOffer.getId()).isEqualTo(test.getId());
        assertThat(updatedOffer.getTitle()).isEqualTo(test2.getTitle());
        assertThat(updatedOffer.getDescription()).isEqualTo(test2.getDescription());
    }

//    @Test
//    void delete_request_deletes_offer() {
//        RideOffer test = new RideOffer("test3", "test description 3");
//        RideOffer created = rideOfferInvoker.createRequest(test);
//        RideOffer createdTest = rideOfferInvoker.getRequest(1);
//        assertThat(createdTest).isNotNull();
//        assertThat(created.getId()).isEqualTo(createdTest.getId());
//        rideOfferInvoker.deleteRequest(createdTest.getId());
//        RideOffer deleted = rideOfferInvoker.getRequest(createdTest.getId());
//        assertThat(deleted).isNull();
//    }
}

