package com.github.asgardbot.chat;

import com.github.asgardbot.core.MessageDispatcher;
import com.github.asgardbot.rqrs.PlaceholderRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PlaceholderEndpoint {

    private MessageDispatcher router;
    private int counter = 0;

    public PlaceholderEndpoint(MessageDispatcher router) {
        this.router = router;
    }

    @Scheduled(fixedRate = 1000 * 5) //every 5 seconds
    private void retrieveRequest() {
        router.enqueueRequest(new PlaceholderRequest().withValue("test " + counter++));
    }
}
