package com.krzyszkowski.yggdrasil.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ExternalApiException extends HttpStatusCodeException {

    private String requestCause;

    public ExternalApiException(HttpStatus statusCode, String requestCause) {
        super(statusCode);
        this.requestCause = requestCause;
    }

    public String getRequestCause() {
        return requestCause;
    }
}
