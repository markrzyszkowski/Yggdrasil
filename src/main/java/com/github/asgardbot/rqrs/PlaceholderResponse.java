package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

public class PlaceholderResponse extends Response {

    private String value;

    public String getValue() {
        return value;
    }

    public PlaceholderResponse withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "PlaceholderResponse{" +
               "value='" + value + '\'' +
               '}';
    }
}
