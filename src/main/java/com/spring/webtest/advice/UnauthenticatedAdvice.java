package com.spring.webtest.advice;

import com.spring.webtest.exception.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UnauthenticatedAdvice {
    @ResponseBody
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unauthenticatedHandler(UnauthenticatedException ex) {
        return ex.getMessage();
    }
}
