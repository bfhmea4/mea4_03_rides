package com.spring.webtest.controller;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.LoginDto;
import com.spring.webtest.dto.TokenDto;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.UserService;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService service;
    private final ModelMapper modelMapper;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    public UserController(UserService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
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
            TokenDto tokenDto = this.service.loginUser(loginDto);
            logger.info("user " + loginDto.getEmail() + " logged in successfully, sending token");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } catch (JoseException | IllegalAccessException e) {
            logger.warning("Could not login user with email: " + loginDto.getEmail());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
//    @GetMapping("api/user")
//    ResponseEntity<List<UserDto>> getAll() {
//        System.out.println("******\nController: Try to get all users...\n******");
//        List<User> users = service.getAll();
//        List<UserDto> userDtos = users.stream()
//                .map(user -> modelMapper.map(user, UserDto.class))
//                .toList();
//        return new ResponseEntity<>(userDtos, HttpStatus.OK);
//    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user/{id}")
    ResponseEntity<UserDto> getById(@PathVariable long id) {
        logger.info("******\nController: Try to get user with id: " + id + "\n******");
        try {
            User user = service.getById(id);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user/byToken")
    ResponseEntity<UserDto> getByToken() {
        logger.info("Getting user by token");
        try {
            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
            User user = service.getByToken(token);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (ResourceNotFoundException | MalformedClaimException | IllegalAccessException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/user")
    ResponseEntity<TokenDto> create(@RequestBody User user) {
        logger.info("******\nController: Try to save User: " + user.getEmail() + "\n******");
        try {
            TokenDto tokenDto = service.save(user);
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } catch (JoseException e) {
            logger.warning("could not generate token");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("api/user")
    ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        logger.info("******\nController: Try to update User with id: " + userDto.getId() + "\n******");
        User savedUser;
        try {
            User user = modelMapper.map(userDto, User.class);
            savedUser = service.update(user);
        } catch (MalformedClaimException | IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return new ResponseEntity<>(savedUserDto, HttpStatus.OK);
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("api/user/{id}")
    ResponseEntity<Void> delete(@PathVariable long id) {
        logger.info("******\nController: Try to delete User: " + id + "\n******");
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalAccessException | MalformedClaimException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
