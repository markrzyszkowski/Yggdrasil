package com.krzyszkowski.yggdrasil.rqrs;

import com.krzyszkowski.yggdrasil.commons.Request;

public class UppercaseRequest extends Request<UppercaseRequest> {

    private String value;

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "UppercaseRequest{" + "value='" + value + "'}";
    }

    public UppercaseRequest withValue(String value) {
        this.value = value;
        return this;
    }
}