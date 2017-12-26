package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class TranslationRequest extends Request {

    private String translationDirection;
    private String text;

    public String getDirection() {
        return translationDirection;
    }

    public String getText() {
        return text;
    }

    public TranslationRequest withDirection(String direction) {
        this.translationDirection = direction;
        return this;
    }

    public TranslationRequest withText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return "TranslationRequest{" +
               "translationDirection='" + translationDirection + '\'' +
               ", text='" + text + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
