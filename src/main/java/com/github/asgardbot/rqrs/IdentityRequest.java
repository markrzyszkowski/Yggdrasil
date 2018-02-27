package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class IdentityRequest extends Request<IdentityRequest> {

    private String text;

    public IdentityRequest withText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }
}
