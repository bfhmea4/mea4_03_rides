package com.spring.webtest.dto;

public class AddressDto {

    private Long id;

    private String street;

    private String houseNumber;

    private long postalCode;

    private String location;

    public AddressDto(Long id, String street, String houseNumber, long postalCode, String location) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.location = location;
    }

    public AddressDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
