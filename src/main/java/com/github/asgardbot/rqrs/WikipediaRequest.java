package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class WikipediaRequest extends Request<WikipediaRequest> {

    private String query;

    public WikipediaRequest withQuery(String query) {
        this.query = query;
        return this;
    }

    @Override
    public String toString() {
        return "WikipediaRequest{" +
               "query='" + query + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }

    public String getQuery() {
        return query;
    }
}
