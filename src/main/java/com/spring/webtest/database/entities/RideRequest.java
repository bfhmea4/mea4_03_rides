package com.spring.webtest.database.entities;

import javax.persistence.*;

@Entity
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne(targetEntity = User.class)
    private User user;

    protected RideRequest() {
    }

    public RideRequest(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public RideRequest(Long id, String title, String description,User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
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

    @Override
    public String toString() {
        return "RideRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
