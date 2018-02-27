package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class WikipediaRequest extends Request<WikipediaRequest> {

    private String query;

    public WikipediaRequest withQuery(String query) {
        this.query = Character.toUpperCase(query.charAt(0)) + query.substring(1);
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
