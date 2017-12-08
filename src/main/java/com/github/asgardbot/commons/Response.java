package com.github.asgardbot.commons;

public abstract class Response {

    protected String transactionId;

    public abstract String getResponseText();

    public Response withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
