package com.github.asgardbot.commons;

public abstract class Request {

    protected String transactionId;

    public Request withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
