package com.spring.webtest.repositories;

import java.util.List;

import com.spring.webtest.model.FizzBuzzCall;
import org.springframework.data.repository.CrudRepository;

public interface FizzBuzzCallRepository extends CrudRepository<FizzBuzzCall, Long> {

    List<FizzBuzzCall> findByNumber(int number);

    FizzBuzzCall findById(long id);



}
