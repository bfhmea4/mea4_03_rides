package com.spring.webtest.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {

    private final String PEPPER = "Pepper and Salt go Hand in Hand";

    public String hash(String pw) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not get algorithm");
            throw new RuntimeException(e);
        }
        pw = pw.concat(PEPPER);
        return new String(digest.digest(pw.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

}
