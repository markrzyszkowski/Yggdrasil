package com.krzyszkowski.yggdrasil.chat;

import com.krzyszkowski.yggdrasil.commons.Response;

public interface ResponseDispatcher {

    void enqueueResponse(Response response);

    void awaitResponse(String transactionId);
}
