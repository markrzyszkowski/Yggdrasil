package com.github.asgardbot.commons;

public abstract class Response {

    protected String transactionId;

    public Response(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public abstract String getResponseText();
}
