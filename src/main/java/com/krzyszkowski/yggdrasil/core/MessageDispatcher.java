package com.krzyszkowski.yggdrasil.core;

import com.krzyszkowski.yggdrasil.commons.Request;

public interface MessageDispatcher {

    void enqueueRequest(Request request);
}
