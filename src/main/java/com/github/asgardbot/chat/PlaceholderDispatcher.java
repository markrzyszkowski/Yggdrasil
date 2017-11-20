package com.github.asgardbot.chat;

import com.github.asgardbot.commons.Response;
import com.github.asgardbot.rqrs.PlaceholderResponse;
import org.springframework.stereotype.Component;

@Component
public class PlaceholderDispatcher implements ResponseDispatcher {

    @Override
    public void enqueueResponse(Response response) {
        if (response instanceof PlaceholderResponse) {
            PlaceholderResponse rs = (PlaceholderResponse) response;
            System.out.println("TEST RESPONSE: " + rs.getValue());
        }
    }
}
