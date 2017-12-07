package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

public class ErrorResponse extends Response {

    private Throwable cause;
    private String message;

    public ErrorResponse(String message, Throwable cause) {
        this.cause = cause;
        this.message = message;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getResponseText() {
        return "Invalid response: " + getMessage();
    }
}
