package com.spring.webtest.controller;

import com.google.gson.Gson;
import com.spring.webtest.FizzBuzz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FizzBuzzController {

    private final FizzBuzz fb = new FizzBuzz();
    private static final Gson gson = new Gson();

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"} )
    @GetMapping("/fizzbuzz/{number}")
    @ResponseBody
    ResponseEntity<String> fizzBuzz(@PathVariable int number) {
        System.out.println("getting FizzBuzz for number :" + String.valueOf(number));
        return ResponseEntity.ok(gson.toJson(fb.fizzBuzz(number))) ;
    }

}
