package com.stackroute.exception;


/*Exception class , it will display the error message when user is not found in the database*/

public class UserNotFound extends Exception {
    public String errorMsg;

    public UserNotFound() {
    }

    public UserNotFound(String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }
}
