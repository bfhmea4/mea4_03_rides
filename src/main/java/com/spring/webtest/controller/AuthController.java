package com.spring.webtest.controller;

import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/api/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {
        System.out.println("Login User with email: " + loginDto.getEmail());
        if (loginDto.getEmail() == null || loginDto.getEmail().equals("")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        UserDto dto = this.service.compareCredentials(loginDto);
        if (dto == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
