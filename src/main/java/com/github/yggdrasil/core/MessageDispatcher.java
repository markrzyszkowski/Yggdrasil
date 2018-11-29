package com.github.yggdrasil.core;

import com.github.yggdrasil.commons.Request;

public interface MessageDispatcher {

    void enqueueRequest(Request request);
}
