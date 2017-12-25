package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

import java.util.ArrayList;
import java.util.List;

public class NewsResponse extends Response {

    private List<String> headlines = new ArrayList<>();

    public NewsResponse withHeadline(String headline) {
        headlines.add(headline);
        return this;
    }

    @Override
    public String getResponseText() {
        StringBuilder builder = new StringBuilder();
        if (!headlines.isEmpty()) {
            headlines.forEach(headline -> builder.append(headline).append(System.lineSeparator()));
        } else {
            builder.append("No articles found");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
               "headlines=" + headlines +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
