package com.spring.webtest.controller;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:4200"})
    @GetMapping("/user")
    @ResponseBody
    ResponseEntity<List<UserDto>> getAll() {
        System.out.println("******\nController: Try to get all users..." + "\n******");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    ResponseEntity<UserDto> get(@PathVariable long id) {
        System.out.println("******\nController: Try to get user with id" + id + "\n******");
        return ResponseEntity.ok(service.getById(id));
    }
}
