package com.krzyszkowski.yggdrasil.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
class RestEndpoint {

    private RestDispatcher dispatcher;
    private RequestReceiver receiver;
    private Logger LOGGER = LoggerFactory.getLogger(RestEndpoint.class);

    public RestEndpoint(RestDispatcher dispatcher, RequestReceiver receiver) {
        this.dispatcher = dispatcher;
        this.receiver = receiver;
    }

    @GetMapping("/request/{session}/{query}")
    @ResponseBody
    public RedirectView retrieveRequest(@PathVariable String query, @PathVariable String session) {
        LOGGER.info("New request from REST");
        LOGGER.debug("'{}' '{}'", session, query);

        receiver.handleMessage(session, query, dispatcher);

        return new RedirectView("/responses");
    }
}
