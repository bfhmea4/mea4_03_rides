package com.spring.webtest.dto;


public class RideOfferDto {

    private long id;
    private String title;
    private String description;

    private UserDto user;


    public RideOfferDto(long id, String title, String description, UserDto user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public RideOfferDto() { }

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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
