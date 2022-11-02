package com.spring.webtest.controller;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.AuthDto;
import com.spring.webtest.dto.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

//    final AuthenticationManager authManager;
//
//    final JwtTokenUtil jwtUtil;
//
//    public AuthController(AuthenticationManager authManager, JwtTokenUtil jwtUtil) {
//        this.authManager = authManager;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
//    @PostMapping("/api/login")
//    public ResponseEntity<?> login(@RequestBody LoginDto login) {
//        try {
//            Authentication auth = authManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            login.getEmail(), login.getPassword()
//                    )
//            );
//
//            User user = (User) auth.getPrincipal();
//            String token = jwtUtil.generateAccessToken(user);
//            AuthDto authDto = new AuthDto(user.getId(), token);
//            return ResponseEntity.ok().body(authDto);
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

}
