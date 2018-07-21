package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.UppercaseRequest;
import com.github.asgardbot.rqrs.UppercaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UppercaseDataProvider extends AbstractRestfulDataProvider {

    private Environment environment;
    private Logger LOGGER = LoggerFactory.getLogger(UppercaseDataProvider.class);

    public UppercaseDataProvider(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof UppercaseRequest;
    }

    @Override
    protected String prepareRequest(Request request) {
        UppercaseRequest rq = (UppercaseRequest)request;
        return String.format("http://localhost:%s/internal/upper/%s",
                             environment.getProperty("local.server.port"),
                             rq.getValue());
    }

    @Override
    protected UppercaseResponse processResponse(String responsePayload) {
        return new UppercaseResponse().withValue(responsePayload);
    }

    @RequestMapping("/internal/upper/{payload}")
    @ResponseBody
    public String makeUppercase(@PathVariable String payload) {
        return payload.toUpperCase();
    }
}
