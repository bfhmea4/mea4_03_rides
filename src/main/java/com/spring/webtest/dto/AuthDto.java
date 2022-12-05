package com.spring.webtest.dto;

public class AuthDto {

    private long userId;
    private String accessToken;

    public AuthDto(long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }
}
