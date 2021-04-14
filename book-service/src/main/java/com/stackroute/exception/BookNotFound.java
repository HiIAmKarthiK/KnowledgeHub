package com.stackroute.exception;

/*This an Exception class , it will throw error message when book is not found in the database */
public class BookNotFound extends Exception {
    public String error;

    public BookNotFound() {

    }

    public BookNotFound(String error) {
        super();
        this.error = error;
    }
}