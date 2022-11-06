package com.spring.webtest.database.entities;

import com.spring.webtest.dto.UserDto;

import javax.persistence.*;
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

    @ManyToOne(targetEntity = User.class)
    private User user;


    public RideOffer() {
    }

    public RideOffer(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }


    public RideOffer(long id, String title, String description, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "RideOffer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user.toString() +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RideOffer rideOffer = (RideOffer) o;

        if (id != rideOffer.id) return false;
        if (!Objects.equals(title, rideOffer.title)) return false;
        return Objects.equals(description, rideOffer.description);
    }


}
