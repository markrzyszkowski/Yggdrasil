package com.github.yggdrasil.commons;

public class InvalidResponseException extends Exception {

    private Response cause;

    public InvalidResponseException(Response cause) {
        this();
        this.cause = cause;
    }

    public InvalidResponseException() {
    }

    public Response getResponse() {
        return cause;
    }
}
