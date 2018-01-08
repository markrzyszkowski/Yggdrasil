package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

import java.util.List;

public class WikipediaResponse extends Response {

    private String text;

    public WikipediaResponse withText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return "WikipediaResponse{" +
               "text='" + text + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }

    @Override
    public List<String> getMessages() {
        return List.of(text);
    }
}
