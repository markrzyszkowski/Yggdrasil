package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Request;

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
