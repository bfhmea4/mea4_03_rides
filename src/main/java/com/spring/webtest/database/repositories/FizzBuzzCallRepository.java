package com.spring.webtest.database.repositories;

import java.util.List;

import com.spring.webtest.database.entities.FizzBuzzCall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FizzBuzzCallRepository extends CrudRepository<FizzBuzzCall, Long> {

    List<FizzBuzzCall> findByNumber(int number);

    FizzBuzzCall findById(long id);



}
