package com.spring.webtest.controller;

import com.spring.webtest.FizzBuzz;
import com.spring.webtest.model.FizzBuzzCall;
import com.spring.webtest.repositories.FizzBuzzCallRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Logger;

@RestController
public class FizzBuzzController {

    private final FizzBuzz fb = new FizzBuzz();

    private static final Logger logger = Logger.getLogger(FizzBuzzController.class.getName());

    private final FizzBuzzCallRepository repository;

    FizzBuzzController(FizzBuzzCallRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @GetMapping(value = "/fizzbuzz/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> fizzBuzz(@PathVariable int number) {
        logger.info("getting FizzBuzz for number :" + number);
        FizzBuzzCall fizzBuzzCall = new FizzBuzzCall(Timestamp.from(Instant.now()), number);
        repository.save(fizzBuzzCall);
        return new ResponseEntity<>(fb.fizzBuzz(number), HttpStatus.OK);
    }

}
