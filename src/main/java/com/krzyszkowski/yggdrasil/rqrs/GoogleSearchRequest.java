package com.krzyszkowski.yggdrasil.rqrs;

import com.krzyszkowski.yggdrasil.commons.Request;

public class GoogleSearchRequest extends Request<GoogleSearchRequest> {

    private String query;
    private String type;

    public String getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }

    public GoogleSearchRequest withQuery(String query) {
        this.query = query;
        return this;
    }

    public GoogleSearchRequest withType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "GoogleSearchRequest{" +
               "query='" + query + '\'' +
               ", type='" + type + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
