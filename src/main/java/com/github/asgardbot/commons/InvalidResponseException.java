package com.github.asgardbot.commons;

public class InvalidResponseException extends Exception {

    private Response cause;

    public InvalidResponseException(Response cause) {
        this.cause = cause;
    }

    public Response getResponse() {
        return cause;
    }
}
