package com.github.asgardbot.commons;

import java.util.List;

public abstract class Response {

    protected String transactionId;

    public abstract List<String> getMessages();

    public Response withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
