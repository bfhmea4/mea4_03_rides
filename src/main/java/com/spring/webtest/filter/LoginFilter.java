package com.spring.webtest.filter;

import com.spring.webtest.security.UserPrincipal;
import com.spring.webtest.security.UserPrincipalService;
import com.spring.webtest.service.AuthService;
import org.jose4j.jwt.JwtClaims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class LoginFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(LoginFilter.class.getName());

    private final UserPrincipalService userPrincipalService;
    private final AuthService authService;

    public LoginFilter(UserPrincipalService userPrincipalService, AuthService authService) {
        this.userPrincipalService = userPrincipalService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("***********\nLOGIN Filter\n*************");

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.log(java.util.logging.Level.INFO, "No Bearer token found in Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        JwtClaims claims = authService.getClaimsFromToken(token);

        if (claims == null) {
            logger.log(Level.INFO, "Token is invalid");
            filterChain.doFilter(request, response);
            return;
        }

        String email = (String) claims.getClaimValue("email");

        if (email == null) {
            logger.log(Level.INFO, "No email found in token");
            filterChain.doFilter(request, response);
            return;
        }

        UserPrincipal user = userPrincipalService.loadUserByUsername(email);

        if (user == null) {
            logger.log(Level.INFO, "No user found with email {0}", email);
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.log(Level.INFO, "Successfully authenticated user {0}", email);

        filterChain.doFilter(request, response);
    }
}
