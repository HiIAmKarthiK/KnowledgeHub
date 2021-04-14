package com.stackroute.exception;

/*This is an Exception class to return Meaning Not Found Exception, when meaning of the word is not present.*/
public class MeaningNotFound extends Exception {
    String noMeaning;

    public MeaningNotFound(String noMeaning) {

    }

    public MeaningNotFound() {
    }
}
