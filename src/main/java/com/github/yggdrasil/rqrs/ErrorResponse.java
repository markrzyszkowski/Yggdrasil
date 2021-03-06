package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Response;

import java.util.List;

public class ErrorResponse extends Response<ErrorResponse> {

    private Throwable cause;
    private String message;

    public ErrorResponse(String message, Throwable cause) {
        this.cause = cause;
        this.message = message;
    }

    public Throwable getCause() {
        return cause;
    }

    @Override
    public List<String> getMessages() {
        return List.of("Invalid response: " + getMessage());
    }

    public String getMessage() {
        return message;
    }
}
