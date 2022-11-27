package com.spring.webtest.controller;

import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.service.AuthService;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        logger.info("Login User with email: " + loginDto.getEmail());
        if (loginDto.getEmail() == null || loginDto.getEmail().equals("")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            String token = this.authService.loginUser(loginDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (JoseException e) {
            logger.warning("Could not login user with email: " + loginDto.getEmail());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
