package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class YouTubeRequest extends Request<YouTubeRequest> {

    private String query;

    public String getQuery() {
        return query;
    }

    public YouTubeRequest withQuery(String query) {
        this.query = query;
        return this;
    }

    @Override
    public String toString() {
        return "YouTubeRequest{" +
               ", query='" + query + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
