package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;
import com.google.api.services.customsearch.model.Result;

import java.util.List;

public class GoogleSearchResponse extends Response {

    private List<Result> results;

    public GoogleSearchResponse withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    @Override
    public String getResponseText() {
        StringBuilder builder = new StringBuilder();
        if (results != null && !results.isEmpty()) {
            results.stream().limit(5).forEach(result -> builder.append(result.getTitle())
                                                               .append(": ")
                                                               .append(result.getLink())
                                                               .append(System.lineSeparator()));
        } else {
            builder.append("No items matching query found");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "GoogleSearchResponse{" +
               "results=" + results +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
