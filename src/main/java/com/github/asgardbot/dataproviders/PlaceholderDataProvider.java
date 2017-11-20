package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.ServiceId;
import com.github.asgardbot.rqrs.PlaceholderRequest;
import com.github.asgardbot.rqrs.PlaceholderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PlaceholderDataProvider implements DataProvider {

    private Logger LOGGER = LoggerFactory.getLogger(PlaceholderDataProvider.class);

    @Override
    public PlaceholderResponse process(Request request) throws InvalidRequestException {
        LOGGER.debug("Attempting to process a request");
        if (request instanceof PlaceholderRequest) {
            PlaceholderRequest rq = (PlaceholderRequest) request;

            LOGGER.info("Request is PlaceholderRequest, able to process");
            LOGGER.debug(request.toString());

            return new PlaceholderResponse().withValue("Placeholder processing " + rq.getValue());
        } else {
            LOGGER.debug("Not a PlaceholderRequest");
            return null;
        }
    }

    @Override
    public ServiceId getServiceId() {
        return new ServiceId("placeholder");
    }
}
