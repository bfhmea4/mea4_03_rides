package com.spring.webtest.filter;

import com.spring.webtest.context.UserContext;
import com.spring.webtest.dto.UserDto;
import com.spring.webtest.service.UserService;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(2)
public class LoginFilter implements Filter {

    UserService userService;

    @Autowired
    UserContext userContext;

    public LoginFilter(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("***********\nLOGIN Filter\n*************");

        String token = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        System.out.println("got token inside filter: " + token);

        try {
            UserDto userDto = userService.getByToken(token);
            userContext.setUserContext();


        } catch (MalformedClaimException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
