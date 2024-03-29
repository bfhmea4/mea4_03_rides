package com.spring.webtest.database.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents a simple ride offer
 */

@Entity
public class RideOffer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;

    private LocalDateTime startTime;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.REMOVE})
    private Address fromAddress;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.REMOVE})
    private Address toAddress;


    public RideOffer() {
    }

    public RideOffer(long id, String title, String description, LocalDateTime startTime, User user, Address from, Address to) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.user = user;
        this.fromAddress = from;
        this.toAddress = to;
    }

    public RideOffer(String title, String description, LocalDateTime startTime, User user, Address from, Address to) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.user = user;
        this.fromAddress = from;
        this.toAddress = to;
    }



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
        return "RideOffer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", user=" + user +
                ", fromAddress=" + fromAddress +
                ", toAddress=" + toAddress +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RideOffer rideOffer = (RideOffer) o;
        return (id == rideOffer.id &&
                Objects.equals(title, rideOffer.title) &&
                Objects.equals(description, rideOffer.description) &&
                Objects.equals(startTime, rideOffer.startTime) &&
                Objects.equals(user, rideOffer.user) &&
                Objects.equals(fromAddress, rideOffer.fromAddress) &&
                Objects.equals(toAddress, rideOffer.toAddress));

    }


}
