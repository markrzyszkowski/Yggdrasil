package com.github.asgardbot.commons;

public class InvalidRequestException extends Exception {

    private Request cause;

    public InvalidRequestException(Request cause) {
        this();
        this.cause = cause;
    }

    public InvalidRequestException() {

    }

    public Request getRequest() {
        return cause;
    }
}
