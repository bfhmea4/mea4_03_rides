package com.spring.webtest.address;

import com.spring.webtest.exception.UnauthenticatedException;
import com.spring.webtest.service.AddressService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddressServiceTests {

    @Autowired
    private AddressService service;

    @BeforeAll
    public void init() {
        System.out.println("Initializing AddressServiceTests");
    }


    @Test
    private void getting_an_address_when_not_authenticated_throws_exception() {
        assertThrows(UnauthenticatedException.class, () -> this.service.findAddressById(1L));
    }

}
