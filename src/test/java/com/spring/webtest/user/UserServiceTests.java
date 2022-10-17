package com.spring.webtest.user;

import com.spring.webtest.WebTestApplication;
import com.spring.webtest.controller.UserController;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

interface UserInvoker {
    UserDto getUser(long id);

    UserDto createUser(User user);

    UserDto updateUser(User user);

    void deleteUser(long id);
}

class WebClientUserRequestInvoker implements UserInvoker {

    private final WebTestClient client;

    public WebClientUserRequestInvoker(WebTestClient client) {
        this.client = client;
    }

    static WebClientUserRequestInvoker remoteServer() {
        return new WebClientUserRequestInvoker(WebTestClient.bindToServer().baseUrl("http://localhost:8080").build());
    }

    static WebClientUserRequestInvoker mockServer(UserService service) {
        return new WebClientUserRequestInvoker(WebTestClient.bindToController(new UserController(service)).build());
    }

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

@SpringBootTest(classes = {WebTestApplication.class, UserService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTests {

    @Value("${useRestMode: false}")
    private boolean useRestMode;

    @Autowired
    private UserService service;

    private UserInvoker invoker;

    @BeforeAll
    void setup() {
        if (useRestMode) {
            this.invoker = WebClientUserRequestInvoker.mockServer(service);

        } else {
            this.invoker = WebClientUserRequestInvoker.remoteServer();
        }
    }

    @Test
    void getting_a_user_that_does_not_exist_returns_null() {
        assertThat(invoker.getUser(7777)).isNull();
    }

    @Test
    void creating_a_user_returns_the_new_user() {
        User user = new User("Max", "Muster", "max@gmail.com", "Seestrassse 3600 Thun", "asdf");
        UserDto dto = invoker.createUser(user);
        assertThat(dto).isNotNull();
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void getting_a_user_by_id_returns_the_user() {
        User user = new User("Max", "Muster", "max@gmail.com", "Seestrassse 3600 Thun", "asdf");
        UserDto dto = invoker.createUser(user);
        UserDto dto2 = invoker.getUser(dto.getId());
        assertThat(dto2).isNotNull();
        assertThat(dto2.getId()).isEqualTo(dto.getId());
        assertThat(dto2.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void updating_a_user_returns_the_updated_user() {
        User user = new User("Max", "Muster", "max@gmail.com", "Seestrassse 3600 Thun", "asdf");
        UserDto dto = invoker.createUser(user);
        user.setId(dto.getId());
        user.setEmail("max-updated@gmail.com");
        UserDto updatedDto = invoker.updateUser(user);
        assertThat(updatedDto).isNotNull();
        assertThat(updatedDto.getId()).isEqualTo(dto.getId());
        assertThat(updatedDto.getEmail()).isEqualTo("max-updated@gmail.com");
    }

    @Test
    void deleting_a_user_removes_the_user() {
        User user = new User("Max", "Muster", "max@gmail.com", "Seestrassse 3600 Thun", "asdf");
        UserDto dto = invoker.createUser(user);
        UserDto dto2 = invoker.getUser(dto.getId());
        assertThat(dto2).isNotNull();
        invoker.deleteUser(dto2.getId());
        UserDto deletedDto = invoker.getUser(dto2.getId());
        assertThat(deletedDto).isNull();
    }

}
