package com.spring.webtest.controller;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.exception.ResourceNotFoundException;
import com.spring.webtest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserController {

    private final UserService service;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    public UserController(UserService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user")
    ResponseEntity<List<UserDto>> getAll() {
        System.out.println("******\nController: Try to get all users..." + "\n******");
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user/{id}")
    ResponseEntity<UserDto> get(@PathVariable long id) {
        System.out.println("******\nController: Try to get user with id: " + id + "\n******");
        try {
            UserDto userDto = service.getById(id);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/user")
    ResponseEntity<UserDto> create(@RequestBody User user) {
        System.out.println("******\nController: Try to save User: " + user.getFirstName() + "\n******");
        UserDto userDto = service.save(user);
        System.out.println(userDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("api/user")
    ResponseEntity<UserDto> update(@RequestBody User user) {
        System.out.println("******\nController: Try to update User with id: " + user.getId() + "\n******");
        UserDto userDto = service.update(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("api/user/{id}")
    ResponseEntity<Void> delete(@PathVariable long id) {
        System.out.println("******\nController: Try to delete User: " + id + "\n******");
        service.delete(id);
        return ResponseEntity.noContent().<Void>build();
    }
}
