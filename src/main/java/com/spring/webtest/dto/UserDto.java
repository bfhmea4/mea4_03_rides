package com.spring.webtest.dto;


public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;

    public UserDto(long id, String firstName, String lastName, String email, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public UserDto() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;
        if (id != userDto.id) return false;
        if (!firstName.equals(userDto.firstName)) return false;
        if (!lastName.equals(userDto.lastName)) return false;
        return (email.equals(userDto.email));
        //TODO Add address to equals method after it is correctly saved in DB
//        return (address.equals(userDto.address));
    }


}
