package com.itm.ithive.exceptions;

public class BlogNotFound extends RuntimeException {
    public BlogNotFound(String message) {
        super(message);
    }
}
