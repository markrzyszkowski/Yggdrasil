package com.github.asgardbot.core;

import com.github.asgardbot.chat.ResponseDispatcher;
import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;
import com.github.asgardbot.dataproviders.DataProvider;
import com.github.asgardbot.rqrs.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
@Primary
public class MessageRouter implements MessageDispatcher {

    private DataProvider serviceResolver;
    private ResponseDispatcher responseDispatcher;
    private Logger LOGGER = LoggerFactory.getLogger(MessageRouter.class);
    private BlockingQueue<Request> requests = new ArrayBlockingQueue<>(25);

    public MessageRouter(ServiceResolver serviceResolver, ResponseDispatcher responseDispatcher) {
        this.serviceResolver = serviceResolver;
        this.responseDispatcher = responseDispatcher;
    }

    @Override
    public void enqueueRequest(Request request) {
        LOGGER.info("Request enqueued");
        requests.add(request);
    }

    @Scheduled(fixedRate = 1000)
    private void process() {
        if (!requests.isEmpty()) {
            LOGGER.debug("Attempting to process a request");
            Request request = requests.poll();
            Response response;
            try {
                response = serviceResolver.process(request);
            } catch (InvalidRequestException e) {
                response = new ErrorResponse("Could not process request ", e);
                LOGGER.error("Invalid request {}", e.getRequest());
            }

            responseDispatcher.enqueueResponse(response);
        } else {
            LOGGER.debug("Request queue empty");
        }
    }
}
