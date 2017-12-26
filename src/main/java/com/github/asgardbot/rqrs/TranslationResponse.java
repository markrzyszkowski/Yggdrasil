package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

public class TranslationResponse extends Response {

    private String text;

    public TranslationResponse withText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String getResponseText() {
        return text;
    }

    @Override
    public String toString() {
        return "TranslationResponse{" +
               "text='" + text + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
