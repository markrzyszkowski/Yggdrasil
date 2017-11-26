package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.ServiceId;
import com.github.asgardbot.rqrs.UppercaseRequest;
import com.github.asgardbot.rqrs.UppercaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UppercaseDataProvider implements DataProvider {

    private Logger LOGGER = LoggerFactory.getLogger(UppercaseDataProvider.class);

    @Override
    public UppercaseResponse process(Request request) throws InvalidRequestException {
        LOGGER.debug("Attempting to process a request");
        if (request instanceof UppercaseRequest) {
            UppercaseRequest rq = (UppercaseRequest) request;

            LOGGER.info("Request is UppercaseRequest, able to process");
            LOGGER.debug(request.toString());

            return new UppercaseResponse(rq.getTransactionId()).withValue(rq.getValue().toUpperCase());
        } else {
            LOGGER.debug("Not a UppercaseRequest");
            return null;
        }
    }

    @Override
    public ServiceId getServiceId() {
        return new ServiceId("uppercase");
    }
}
