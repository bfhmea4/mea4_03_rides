package com.spring.webtest.security;

import com.spring.webtest.exception.UnauthenticatedException;
import com.spring.webtest.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

    public UserPrincipal getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
            return null;

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserPrincipal))
            return null;

        return (UserPrincipal) principal;
    }

    public void assureIsAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal) || !authentication.isAuthenticated())
            throw new UnauthenticatedException("User is not authenticated");
    }

    public void assureHasId(Long id) {
        assureIsAuthenticated();
        UserPrincipal user = getUser();
        if (user == null || user.getId() != id)
            throw new UnauthorizedException("User is not authorized");
    }
}
