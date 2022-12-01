package com.spring.webtest.controller;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.TokenDto;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.UserService;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.logging.Logger;

@RestController
public class UserController {

    private final UserService service;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    public UserController(UserService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @RequestMapping(
            value = "/api/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        logger.info("Login User with email: " + loginDto.getEmail());
        if (loginDto.getEmail() == null || loginDto.getEmail().equals("")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String token = this.service.loginUser(loginDto);
            return new ResponseEntity<>(new TokenDto(token), HttpStatus.OK);
        } catch (JoseException | IllegalAccessException e) {
            logger.warning("Could not login user with email: " + loginDto.getEmail());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
//    @GetMapping("api/user")
//    ResponseEntity<List<UserDto>> getAll() {
//        System.out.println("******\nController: Try to get all users..." + "\n******");
//        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
//    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user/{id}")
    ResponseEntity<UserDto> getById(@PathVariable long id) {
        logger.info("******\nController: Try to get user with id: " + id + "\n******");
        try {
            UserDto userDto = service.getById(id);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user/byToken")
    ResponseEntity<UserDto> getByToken() {
        logger.info("Getting user by token");
        try {
            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
            UserDto userDto = service.getByToken(token);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (ResourceNotFoundException | MalformedClaimException | IllegalAccessException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/user")
    ResponseEntity<UserDto> create(@RequestBody User user) {
        logger.info("******\nController: Try to save User: " + user.getFirstName() + "\n******");
        UserDto userDto = service.save(user);
        System.out.println(userDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("api/user")
    ResponseEntity<UserDto> update(@RequestBody User user) {
        logger.info("******\nController: Try to update User with id: " + user.getId() + "\n******");
        UserDto userDto = null;
        try {
            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
            userDto = service.update(user, token);
        } catch (MalformedClaimException | IllegalAccessException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("api/user/{id}")
    ResponseEntity<Void> delete(@PathVariable long id) {
        logger.info("******\nController: Try to delete User: " + id + "\n******");
        try {
            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
            service.delete(id, token);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NullPointerException | IllegalAccessException | MalformedClaimException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
