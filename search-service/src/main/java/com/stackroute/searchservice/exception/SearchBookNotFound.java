package com.stackroute.searchservice.exception;

/**
 * Search book or Keyword not found in database
 * */
public class SearchBookNotFound  extends Exception{
    public String error;

    public SearchBookNotFound() {

    }

    public SearchBookNotFound(String error) {
        super();
        this.error = error;
    }
}
