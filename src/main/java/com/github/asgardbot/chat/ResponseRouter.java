package com.github.asgardbot.chat;

import com.github.asgardbot.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
@Primary
public class ResponseRouter implements ResponseDispatcher {

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

    @Scheduled(fixedRate = 1000)
    private void dispatch() {
        if (!responses.isEmpty()) {
            LOGGER.info("Processing a response");
            Response response = responses.poll();
            dispatchers.forEach(dispatcher -> dispatcher.enqueueResponse(response));
        } else {
            LOGGER.debug("Response queue empty");
        }
    }
}