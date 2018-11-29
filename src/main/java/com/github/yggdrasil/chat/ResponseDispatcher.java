package com.github.yggdrasil.chat;

import com.github.yggdrasil.commons.Response;

public interface ResponseDispatcher {

    void enqueueResponse(Response response);

    void awaitResponse(String transactionId);
}
