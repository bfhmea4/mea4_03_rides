package com.spring.webtest.address;

import com.spring.webtest.database.entities.Address;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void getting_an_address_when_not_authenticated_throws_exception() {
        Address testAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        service.addAddress(testAddress);

        Address address = service.findAddressById(1L);
        assertEquals(testAddress, address);
    }

    @Test
    public void updating_an_address_returns_updated_address() {
        Address testAddress = new Address(1L, "Elfistrasse", "11", 3011L, "Bern");
        service.addAddress(testAddress);
        testAddress.setStreet("OtherStreet");

        Address address = service.updateAddress(testAddress);
        assertEquals("OtherStreet", address.getStreet());
    }

}
