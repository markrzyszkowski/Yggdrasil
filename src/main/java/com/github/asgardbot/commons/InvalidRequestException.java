package com.github.asgardbot.commons;

public class InvalidRequestException extends Exception {

    private Request cause;

    public InvalidRequestException(Request cause) {
        this.cause = cause;
    }

    public Request getRequest() {
        return cause;
    }
}
