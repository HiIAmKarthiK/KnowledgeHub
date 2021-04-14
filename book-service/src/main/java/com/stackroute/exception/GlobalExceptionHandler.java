package com.stackroute.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*This is an exception class, which throws error message when the book could not found.*/
@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "BookNotFound")
    private String message2;

    /*This method throws error message when the book could not found.*/
    @ExceptionHandler(value = BookNotFound.class)
    public ResponseEntity<String> BookNotFoundException(BookNotFound BookNotFound) {
        return new ResponseEntity<String>(message2, HttpStatus.CONFLICT);
    }
}
