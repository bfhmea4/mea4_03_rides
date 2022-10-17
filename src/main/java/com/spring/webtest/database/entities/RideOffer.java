package com.spring.webtest.database.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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


    public RideOffer() {
    }

    public RideOffer(String title, String description) {
        this.title = title;
        this.description = description;
    }


    public RideOffer(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    @Override
    public String toString() {
        return "RideOffer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
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
