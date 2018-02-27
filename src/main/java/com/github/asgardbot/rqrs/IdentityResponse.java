package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

import java.util.List;

public class IdentityResponse extends Response<IdentityResponse> {

    private String text;

    public static IdentityResponse fromIdRq(IdentityRequest identityRequest) {
        return new IdentityResponse()
          .withText(identityRequest.getText())
          .withTransactionId(identityRequest.getTransactionId());
    }

    public IdentityResponse withText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public List<String> getMessages() {
        return List.of(text);
    }
}
