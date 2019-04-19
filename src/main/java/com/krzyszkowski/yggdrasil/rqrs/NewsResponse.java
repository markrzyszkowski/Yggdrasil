package com.krzyszkowski.yggdrasil.rqrs;

import com.krzyszkowski.yggdrasil.commons.Response;

import java.util.ArrayList;
import java.util.List;

public class NewsResponse extends Response<NewsResponse> {

    private List<String> headlines = new ArrayList<>();

    public NewsResponse withHeadline(String headline) {
        headlines.add(headline);
        return this;
    }

    @Override
    public List<String> getMessages() {
        if (!headlines.isEmpty()) {
            return headlines;
        } else {
            return List.of("No articles found");
        }
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
               "headlines=" + headlines +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
