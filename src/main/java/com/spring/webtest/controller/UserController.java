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

    public UserController(UserService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:4200"})
    @GetMapping("api/user")
    @ResponseBody
    ResponseEntity<List<UserDto>> getAll() {
        System.out.println("******\nController: Try to get all users..." + "\n******");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("api/user/{id}")
    @ResponseBody
    ResponseEntity<UserDto> get(@PathVariable long id) {
        System.out.println("******\nController: Try to get user with id" + id + "\n******");
        return ResponseEntity.ok(service.getById(id));
    }

    @CrossOrigin()
    @PostMapping("api/user")
    @ResponseBody
    ResponseEntity<UserDto> create(@RequestBody User user) {
        System.out.println("******\nController: Try to save User: " + user.getFirstName() + "\n******");
        return ResponseEntity.ok(service.save(user));
    }

    @CrossOrigin
    @PutMapping("api/user")
    @ResponseBody
    ResponseEntity<UserDto> update(@RequestBody User user) {
        System.out.println("******\nController: Try to update User with id: " + user.getId() + "\n******");
        return ResponseEntity.ok(service.update(user));
    }

    @CrossOrigin
    @DeleteMapping("api/user/{id}")
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable long id) {
        System.out.println("******\nController: Try to delete User: " + id + "\n******");
        service.delete(id);
        return ResponseEntity.noContent().<Void>build();
    }


}
