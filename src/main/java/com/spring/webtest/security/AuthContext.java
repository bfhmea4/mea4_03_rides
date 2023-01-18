package com.spring.webtest.security;

import com.spring.webtest.exception.UnauthenticatedException;
import com.spring.webtest.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

    public UserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
            return null;

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails))
            return null;

        return (UserDetails) principal;
    }

    public void assureIsAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() instanceof String || !authentication.isAuthenticated()) {
            throw new UnauthenticatedException("User is not authenticated");
        }
    }

    public void assureHasUsername(String username) {
        assureIsAuthenticated();
        UserDetails user = getUser();
        if (user == null || !user.getUsername().equals(username))
            throw new UnauthorizedException("User is not authorized");
    }
}
