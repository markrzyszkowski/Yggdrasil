package com.github.asgardbot.chat;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.core.MessageDispatcher;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.parsing.QueryDto;
import com.github.asgardbot.rqrs.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RestEndpoint {

    private MessageDispatcher router;
    private RestDispatcher dispatcher;
    private Logger LOGGER = LoggerFactory.getLogger(RestEndpoint.class);
    private Parser queryParser;

    public RestEndpoint(MessageDispatcher router,
                        RestDispatcher dispatcher,
                        Parser queryParser) {
        this.router = router;
        this.dispatcher = dispatcher;
        this.queryParser = queryParser;
    }

    @GetMapping("/request/{session}/{query}")
    @ResponseBody
    public RedirectView retrieveRequest(@PathVariable String query, @PathVariable String session) {
        LOGGER.info("New request from REST");
        LOGGER.debug("'{}' '{}'", session, query);

        Request request = queryParser.parse(new QueryDto().withQueryText(query).withSessionId(session));
        dispatcher.awaitResponse(session);

        if (request != null) {
            router.enqueueRequest(request.withTransactionId(session));
        } else {
            LOGGER.error("Failed to parse query '{}'", query);
            dispatcher.enqueueResponse(new ErrorResponse("Something went wrong", null)
                                         .withTransactionId(session));
        }

        return new RedirectView("/responses");
    }
}
