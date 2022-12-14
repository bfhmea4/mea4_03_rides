package com.spring.webtest.dto;

import java.time.LocalDateTime;

public class RideRequestDto {
    private long id;
    private String title;
    private String description;

    private LocalDateTime startTime;

    private UserDto user;

    private AddressDto fromAddress;

    private AddressDto toAddress;


    public RideRequestDto(long id, String title, String description, LocalDateTime startTime, UserDto user, AddressDto fromAddress, AddressDto toAddress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.user = user;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public RideRequestDto() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime time) {
        this.startTime = time;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public AddressDto getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(AddressDto fromAddress) {
        this.fromAddress = fromAddress;
    }

    public AddressDto getToAddress() {
        return toAddress;
    }

    public void setToAddress(AddressDto toAddress) {
        this.toAddress = toAddress;
    }
}
