package com.github.asgardbot.core;

import com.github.asgardbot.chat.ResponseDispatcher;
import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.InvalidResponseException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;
import com.github.asgardbot.dataproviders.DataProvider;
import com.github.asgardbot.rqrs.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
class MessageRouter implements MessageDispatcher {

    private DataProvider serviceOrchestrator;
    private ResponseDispatcher responseDispatcher;
    private Logger LOGGER = LoggerFactory.getLogger(MessageRouter.class);
    private BlockingQueue<Request> requests = new ArrayBlockingQueue<>(25);

    public MessageRouter(DataProvider serviceOrchestrator, @Qualifier("router") ResponseDispatcher responseDispatcher) {
        this.serviceOrchestrator = serviceOrchestrator;
        this.responseDispatcher = responseDispatcher;
    }

    @Override
    public void enqueueRequest(Request request) {
        LOGGER.info("Request enqueued");
        requests.add(request);
    }

    @Scheduled(fixedRate = 100)
    private void process() {
        Request request;
        while ((request = requests.poll()) != null) {
            LOGGER.debug("Attempting to process a request");
            Response response;
            try {
                response = Optional.ofNullable(serviceOrchestrator.process(request))
                                   .orElseThrow(InvalidResponseException::new);
            } catch (InvalidRequestException e) {
                response = new ErrorResponse("Could not process request ", e);
                LOGGER.error("Invalid request '{}'", e.getRequest());
            } catch (InvalidResponseException e) {
                response = new ErrorResponse("Could not process response", e);
                LOGGER.error("Invalid response '{}'", e.getResponse());
            } catch (HttpStatusCodeException e) {
                response = new ErrorResponse("Server responded with an error code " + e.getStatusCode(), e);
                LOGGER.error("Error code '{}' from external API for request '{}'", e.getStatusCode(), request);
            } catch (Exception e) {
                response = new ErrorResponse("General error", e);
                LOGGER.error("Unknown error occurred ", e);
            }

            response.withTransactionId(request.getTransactionId());
            responseDispatcher.enqueueResponse(response);
        }
    }
}
