package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class PlaceholderRequest extends Request {

    private String value;

    public String getValue() {

        return value;
    }

    @Override
    public String toString() {
        return "PlaceholderRequest{" +
               "value='" + value + '\'' +
               '}';
    }

    public PlaceholderRequest withValue(String value) {
        this.value = value;
        return this;
    }
}
