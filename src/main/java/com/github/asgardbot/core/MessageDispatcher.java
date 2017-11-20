package com.github.asgardbot.core;

import com.github.asgardbot.commons.Request;

public interface MessageDispatcher {

    void enqueueRequest(Request request);
}
