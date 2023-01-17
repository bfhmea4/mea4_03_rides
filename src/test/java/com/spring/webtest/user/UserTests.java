package com.spring.webtest.user;


import com.spring.webtest.database.entities.User;
import com.spring.webtest.exception.UnauthenticatedException;
import com.spring.webtest.exception.UnauthorizedException;
import com.spring.webtest.exception.UserNotFoundException;
import com.spring.webtest.service.UserService;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserTests {

    @Autowired
    private UserService service;

    @BeforeAll
    public void init() {
        System.out.println("Initializing UserTests");
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    void getting_a_user_by_id_returns_user() throws JoseException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        service.save(testUser);

        User user = service.getById(1L);
        assertEquals(testUser, user);
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    void getting_a_user_by_email_returns_user() throws JoseException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        service.save(testUser);

        User user = service.getByEmail("myemail@me.com");
        assertEquals(testUser, user);
    }

    @Test
    void getting_a_user_when_not_authorized_throws_exception() {
        assertThrows(UnauthenticatedException.class, () -> service.getById(1L));
    }

    @Test
    @WithMockUser(username = "notmyemail@me.com")
    void getting_a_user_when_authorized_with_different_user_throws_exception() throws JoseException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        service.save(testUser);
        assertThrows(UnauthorizedException.class, () -> service.getById(1L));
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    void getting_a_user_by_id_that_does_not_exist_throws_exception() {
        assertThrows(
                UserNotFoundException.class,
                () -> service.getById(1000L)
        );
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    void getting_a_user_by_email_that_does_not_exist_throws_exception() {
        assertThrows(
                UserNotFoundException.class,
                () -> service.getByEmail("notmyemail@unknown.com")
        );
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    void updating_a_user_returns_the_updated_user() throws JoseException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        service.save(testUser);

        testUser.setAddress("A different address");
        User updatedUser = service.update(testUser);

        assertEquals("A different address", updatedUser.getAddress());
    }

    @Test
    @WithMockUser(username = "myemail@me.com")
    void deleting_a_user_and_getting_it_throws_exception() throws JoseException {
        User testUser = new User(1L, "A", "User", "myemail@me.com", "My Address", "My Password");
        service.save(testUser);

        service.delete(testUser.getId());

        assertThrows(UserNotFoundException.class, () -> service.getById(testUser.getId()));
    }

}