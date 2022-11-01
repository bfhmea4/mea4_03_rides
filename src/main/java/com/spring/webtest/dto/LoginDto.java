package com.spring.webtest.dto;

import com.sun.istack.NotNull;

public class LoginDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
