package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Request;

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
