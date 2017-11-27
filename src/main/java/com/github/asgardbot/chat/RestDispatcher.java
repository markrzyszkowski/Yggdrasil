package com.github.asgardbot.chat;

import com.github.asgardbot.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class RestDispatcher implements ResponseDispatcher {

    private List<String> awaitedResponses = new LinkedList<>();
    private List<String> pendingResponses = new LinkedList<>();
    private Logger LOGGER = LoggerFactory.getLogger(RestDispatcher.class);

    @Override
    public void enqueueResponse(Response response) {
        final String transactionId = response.getTransactionId();

        if (awaitedResponses.contains(transactionId)) {
            LOGGER.info("Response produced for {}", transactionId);
            awaitedResponses.remove(transactionId);
            final String responseText = response.getResponseText();
            LOGGER.debug("Response contents: {}", responseText);
            pendingResponses.add(responseText);
        }
    }

    @Override
    public void awaitResponse(String transactionId) {
        LOGGER.info("Awaiting a response to {}", transactionId);
        awaitedResponses.add(transactionId);
    }

    @GetMapping("/responses")
    public List<String> getResponses() {
        return new ArrayList<>(pendingResponses);
    }

    @GetMapping("/clear")
    public void clear() {
        pendingResponses.clear();
    }
}
