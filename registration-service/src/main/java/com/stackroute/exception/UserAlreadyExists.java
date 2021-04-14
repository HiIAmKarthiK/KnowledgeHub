package com.stackroute.exception;


/*Exception class , it will display the error message when user already exist*/

public class UserAlreadyExists extends Exception {
    public String errorMsg;

    public UserAlreadyExists() {
    }

    public UserAlreadyExists(String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }
}
