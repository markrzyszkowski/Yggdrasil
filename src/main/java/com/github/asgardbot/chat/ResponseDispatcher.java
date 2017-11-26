package com.github.asgardbot.chat;

import com.github.asgardbot.commons.Response;

public interface ResponseDispatcher {

    void enqueueResponse(Response response);

    void awaitResponse(String transactionId);
}
