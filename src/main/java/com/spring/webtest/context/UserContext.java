package com.spring.webtest.context;

import com.spring.webtest.database.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private ThreadLocal<User> user = new ThreadLocal<>();

    public User getUser() {
        return this.user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }
}
