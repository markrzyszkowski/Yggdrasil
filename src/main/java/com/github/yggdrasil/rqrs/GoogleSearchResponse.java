package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Response;
import com.google.api.services.customsearch.model.Result;

import java.util.List;
import java.util.stream.Collectors;

public class GoogleSearchResponse extends Response<GoogleSearchResponse> {

    private List<Result> results;

    public GoogleSearchResponse withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    @Override
    public List<String> getMessages() {
        if (results != null && !results.isEmpty()) {
            return results.stream()
                          .limit(5)
                          .map(result -> result.getTitle() + ": " + result.getLink())
                          .collect(Collectors.toList());
        } else {
            return List.of("No items matching query found");
        }
    }

    @Override
    public String toString() {
        return "GoogleSearchResponse{" +
               "results=" + results +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
