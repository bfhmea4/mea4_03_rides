package com.spring.webtest.controller;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user")
    ResponseEntity<List<UserDto>> getAll() {
        System.out.println("******\nController: Try to get all users...\n******");
        List<User> users = service.getAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping("api/user/{id}")
    ResponseEntity<UserDto> get(@PathVariable long id) {
        System.out.println("******\nController: Try to get user with id: " + id + "\n******");
        User user = service.getById(id);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("api/user")
    ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        System.out.println("******\nController: Try to save User: " + userDto.getEmail() + "\n******");
        User user = modelMapper.map(userDto, User.class);
        User savedUser = service.save(user);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return new ResponseEntity<>(savedUserDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PutMapping("api/user")
    ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        System.out.println("******\nController: Try to update User with id: " + userDto.getId() + "\n******");
        User user = modelMapper.map(userDto, User.class);
        User savedUser = service.update(user);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return new ResponseEntity<>(savedUserDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @DeleteMapping("api/user/{id}")
    ResponseEntity<Void> delete(@PathVariable long id) {
        System.out.println("******\nController: Try to delete User: " + id + "\n******");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
