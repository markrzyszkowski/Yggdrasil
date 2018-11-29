package com.github.yggdrasil.commons;

public abstract class Request<T extends Request<T>> {

    protected String transactionId;

    @SuppressWarnings("unchecked")
    public T withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return (T)this;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
