package com.spring.webtest.controller;

import com.spring.webtest.FizzBuzz;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FizzBuzzController {

    private final FizzBuzz fb = new FizzBuzz();

    @GetMapping("/fizzbuzz/{number}")
    String fizzBuzz(@PathVariable int number) {
        System.out.println("getting FizzBuzz for number :" + String.valueOf(number));
        return fb.fizzBuzz(number);
    }

}
