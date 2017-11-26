package com.github.asgardbot.chat;

import com.github.asgardbot.core.MessageDispatcher;
import com.github.asgardbot.rqrs.UppercaseRequest;
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

    public RestEndpoint(MessageDispatcher router, RestDispatcher dispatcher) {
        this.router = router;
        this.dispatcher = dispatcher;
    }

    @GetMapping("/request/{query}")
    @ResponseBody
    public RedirectView retrieveRequest(@PathVariable String query) {
        String id = LocalDateTime.now().toString();

        LOGGER.info("New request from REST");
        LOGGER.debug("{} {}", id, query);

        router.enqueueRequest(new UppercaseRequest(id).withValue(query));
        dispatcher.awaitResponse(id);

        return new RedirectView("/responses");
    }
}
