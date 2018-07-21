package com.github.asgardbot.chat;

import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.core.MessageDispatcher;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.parsing.QueryDto;
import com.github.asgardbot.rqrs.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RequestReceiver {

    private MessageDispatcher router;
    private Parser queryParser;
    private Logger LOGGER = LoggerFactory.getLogger(RequestReceiver.class);

    public RequestReceiver(MessageDispatcher router, @Qualifier("mainParser") Parser queryParser) {
        this.router = router;
        this.queryParser = queryParser;
    }

    public void handleMessage(String session, String message, ResponseDispatcher responseDispatcher) {
        try {
            responseDispatcher.awaitResponse(session);
            Request request = queryParser.parse(new QueryDto().withQueryText(message).withSessionId(session));
            verifyParsedRequest(request);
            router.enqueueRequest(request.withTransactionId(session));
        } catch (Exception e) {
            LOGGER.error("Failed to parse query '{}'", message);
            responseDispatcher.enqueueResponse(new ErrorResponse("Something went wrong", null)
                                                 .withTransactionId(session));
        }
    }

    private void verifyParsedRequest(Request request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException();
        }
    }
}
