package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

public class UppercaseResponse extends Response {

    private String value;

    @Override
    public String getResponseText() {
        return getValue();
    }

    public String getValue() {
        return value;
    }

    public UppercaseResponse withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "UppercaseResponse{" + "value='" + value + "'}";
    }
}
