package com.spring.webtest.context;

import com.spring.webtest.database.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private ThreadLocal<User> userContext = new ThreadLocal<>();

    public User getUserContext() {
        return this.userContext.get();
    }

    public void setUserContext(User user) {
        this.userContext.set(user);
    }
}
