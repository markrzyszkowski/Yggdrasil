package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Response;

import java.util.List;

public class TranslationResponse extends Response<TranslationResponse> {

    private String text;

    public TranslationResponse withText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public List<String> getMessages() {
        return List.of(text);
    }

    @Override
    public String toString() {
        return "TranslationResponse{" +
               "text='" + text + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
