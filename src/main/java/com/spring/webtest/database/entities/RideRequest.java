package com.spring.webtest.database.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime startTime;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.REMOVE})
    private Address fromAddress;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.REMOVE})
    private Address toAddress;

    protected RideRequest() {
    }

    public RideRequest(Long id, String title, String description, LocalDateTime startTime, User user, Address fromAddress, Address toAddress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.user = user;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }


    public RideRequest(String title, String description, LocalDateTime startTime, User user, Address fromAddress, Address toAddress) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.user = user;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "from_address_id", nullable = false)
    public Address getFromAddress() {
        return fromAddress;
    }


    public void setFromAddress(Address fromAddress) {
        this.fromAddress = fromAddress;
    }

    @OneToOne
    @JoinColumn(name = "to_address_id", nullable = false)
    public Address getToAddress() {
        return toAddress;
    }

    public void setToAddress(Address toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public String toString() {
        return "RideRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", user=" + user +
                ", fromAddress=" + fromAddress +
                ", toAddress=" + toAddress +
                '}';
    }
}
