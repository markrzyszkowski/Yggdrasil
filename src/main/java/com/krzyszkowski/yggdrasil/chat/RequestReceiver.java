package com.krzyszkowski.yggdrasil.chat;

import com.krzyszkowski.yggdrasil.commons.InvalidRequestException;
import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.core.MessageDispatcher;
import com.krzyszkowski.yggdrasil.parsing.Parser;
import com.krzyszkowski.yggdrasil.parsing.QueryDto;
import com.krzyszkowski.yggdrasil.rqrs.ErrorResponse;
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
