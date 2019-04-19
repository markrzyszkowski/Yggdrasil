package com.krzyszkowski.yggdrasil.chat;

import com.krzyszkowski.yggdrasil.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
@Qualifier("router")
class ResponseRouter implements ResponseDispatcher {

    private Logger LOGGER = LoggerFactory.getLogger(ResponseRouter.class);

    private BlockingQueue<Response> responses = new ArrayBlockingQueue<>(25);
    private List<ResponseDispatcher> dispatchers;

    public ResponseRouter(List<ResponseDispatcher> dispatchers) {
        this.dispatchers = dispatchers;
    }

    @Override
    public void enqueueResponse(Response response) {
        LOGGER.info("Response enqueued");
        responses.add(response);
    }

    @Override
    public void awaitResponse(String transactionId) {
        throw new UnsupportedOperationException("Critical error: attempt to force ResponseRouter to directly " +
                                                "interface with a chat engine");
    }

    @Scheduled(fixedRate = 100)
    private void dispatch() {
        while (!responses.isEmpty()) {
            LOGGER.info("Processing a response");
            Response response = responses.poll();
            dispatchers.forEach(dispatcher -> dispatcher.enqueueResponse(response));
        }
    }
}
