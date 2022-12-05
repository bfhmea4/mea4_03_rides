package com.spring.webtest.user;

import com.spring.webtest.controller.UserController;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class UserWebClientInvoker implements UserInvoker {

    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    @Override
    public UserDto getUser(long id) {
        return client.get()
                .uri("/api/user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(UserDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public UserDto createUser(User user) {
        return client.post()
                .uri("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(UserDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public UserDto updateUser(User user) {
        return client.put()
                .uri("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(UserDto.class)
                .getResponseBody().blockFirst();
    }

    @Override
    public void deleteUser(long id) {
        client.delete()
                .uri("/api/user/" + id)
                .exchange()
                .returnResult(UserDto.class)
                .getResponseBody().blockFirst();
    }
}
