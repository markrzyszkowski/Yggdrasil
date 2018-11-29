package com.github.yggdrasil.commons;

import java.util.List;

public abstract class Response<T extends Response<T>> {

    protected String transactionId;

    public abstract List<String> getMessages();

    @SuppressWarnings("unchecked")
    public T withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return (T)this;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
