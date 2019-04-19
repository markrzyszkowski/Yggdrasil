package com.krzyszkowski.yggdrasil.rqrs;

import com.krzyszkowski.yggdrasil.commons.Response;

import java.util.List;

public class UppercaseResponse extends Response<UppercaseResponse> {

    private String value;

    @Override
    public List<String> getMessages() {
        return List.of(getValue());
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
