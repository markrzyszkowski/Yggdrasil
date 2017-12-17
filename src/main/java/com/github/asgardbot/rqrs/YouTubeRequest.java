package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class YouTubeRequest extends Request {

    private String type;
    private String query;

    public String getType() {
        return type;
    }

    public String getQuery() {
        return query;
    }

    public YouTubeRequest withType(String type) {
        this.type = type;
        return this;
    }

    public YouTubeRequest withQuery(String query) {
        this.query = query;
        return this;
    }

    @Override
    public String toString() {
        return "YouTubeRequest{" +
               "type='" + type + '\'' +
               ", query='" + query + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
