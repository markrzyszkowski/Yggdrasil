package com.github.asgardbot.chat;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.core.MessageDispatcher;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.rqrs.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

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

    @GetMapping("/request/{query}")
    @ResponseBody
    public RedirectView retrieveRequest(@PathVariable String query) {
        String id = LocalDateTime.now().toString();

        LOGGER.info("New request from REST");
        LOGGER.debug("{} {}", id, query);

        Request request = queryParser.parse(query);
        dispatcher.awaitResponse(id);

        if (request != null) {
            router.enqueueRequest(request.withTransactionId(id));
        } else {
            LOGGER.error("Invalid query string {}", query);
            dispatcher.enqueueResponse(new ErrorResponse("Invalid query string", null)
                                         .withTransactionId(id));
        }

        return new RedirectView("/responses");
    }
}
