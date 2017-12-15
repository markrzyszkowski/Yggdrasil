package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

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
    public String getResponseText() {
        return text;
    }
}
