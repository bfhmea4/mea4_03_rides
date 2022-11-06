package com.spring.webtest.service;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashService {

    private final String PEPPER = "Pepper and Cheese go Hand in Hand";
    private final int ITERATIONS = 20000;
    private final int WIDTH_IN_BITS = 128;

    public String hash(String pw) {
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(PEPPER, ITERATIONS, WIDTH_IN_BITS);
        encoder.setEncodeHashAsBase64(true);
        System.out.println(pw + " is encoded: " + encoder.encode(pw));
        return encoder.encode(pw);
    }

}
