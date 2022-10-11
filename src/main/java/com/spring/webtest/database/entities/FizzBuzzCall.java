package com.spring.webtest.database.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class FizzBuzzCall {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;
    private int number;

    protected FizzBuzzCall() {}

    public FizzBuzzCall(Timestamp timestamp, int number) {
        this.timestamp = timestamp;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format(
                "FizzBuzzCall[id=%d, timestamp='%s', number='%s']",
                id, timestamp, number
        );
    }

    public Long getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getNumber() {
        return number;
    }
}