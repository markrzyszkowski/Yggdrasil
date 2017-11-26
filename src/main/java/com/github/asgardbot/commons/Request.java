package com.github.asgardbot.commons;

public abstract class Request {

    protected String transactionId;

    public Request(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
