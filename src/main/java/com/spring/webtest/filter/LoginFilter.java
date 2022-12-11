package com.spring.webtest.filter;

import com.spring.webtest.context.UserContext;
import com.spring.webtest.database.entities.User;
import com.spring.webtest.service.AuthService;
import com.spring.webtest.service.UserService;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class LoginFilter implements Filter {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    UserContext userContext;

    public LoginFilter(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("***********\nLOGIN Filter\n*************");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();

        if (url.equals("http://localhost:8080/api/login") ||
                (url.equals("http://localhost:8080/api/user") && request.getMethod().equals("POST")) ||
                request.getMethod().equals("OPTIONS")) {
            System.out.println("Request does not need a token");
            filterChain.doFilter(request, response);
        } else {

            try {

                String token = ((HttpServletRequest) servletRequest).getHeader("Authorization");
                System.out.println("got token inside filter: " + token);
                if (authService.tokenIsValid(token)) {
                    User user = userService.getByToken(token);
                    userContext.setUser(user);
                    System.out.println("User is set inside context");
                }

            } catch (MalformedClaimException | IllegalAccessException e) {
                e.printStackTrace();
                response.setStatus(403);
            }

            filterChain.doFilter(request, response);

        }

    }
}
