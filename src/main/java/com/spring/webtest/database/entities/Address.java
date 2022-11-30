package com.spring.webtest.database.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String houseNumber;

    private long postalCode;

    private String location;

    public Address(Long id, String street, String houseNumber, long postalCode, String location) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.location = location;
    }

    public Address(String street, String houseNumber, long postalCode, String location) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.location = location;
    }

    public Address() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postalCode=" + postalCode +
                ", location='" + location + '\'' +
                '}';
    }
}
