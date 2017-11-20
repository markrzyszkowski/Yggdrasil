package com.github.asgardbot.chat;

import com.github.asgardbot.commons.Response;
import com.github.asgardbot.rqrs.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorDispatcher implements ResponseDispatcher {

    private Logger LOGGER = LoggerFactory.getLogger(ErrorDispatcher.class);

    @Override
    public void enqueueResponse(Response response) {
        if (response instanceof ErrorResponse) {
            ErrorResponse error = (ErrorResponse) response;
            LOGGER.error("An error response was sent, caused by {} {}", error.getMessage(), error.getCause());
        }
    }
}
