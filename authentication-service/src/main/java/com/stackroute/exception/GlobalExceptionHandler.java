package com.stackroute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*This is an exception class, which throws error message upon giving invalid credentials*/
@ControllerAdvice
public class GlobalExceptionHandler {
    /*This method throws error message upon giving invalid credentials*/
    @ExceptionHandler(value = InvalidCredentials.class)
    public ResponseEntity<String> invalidCredentials(InvalidCredentials invalidCredentials) {
        System.out.println("invalid");
        return new ResponseEntity<>("Invalid Credentials", HttpStatus.CONFLICT);
    }

}