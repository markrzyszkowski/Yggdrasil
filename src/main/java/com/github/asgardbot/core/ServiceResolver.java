package com.github.asgardbot.core;

import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.InvalidResponseException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;
import com.github.asgardbot.dataproviders.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class ServiceResolver implements DataProvider {

    private Logger LOGGER = LoggerFactory.getLogger(ServiceResolver.class);
    private List<DataProvider> services;

    public ServiceResolver(List<DataProvider> services) {
        this.services = services;
    }

    @Override
    public Response process(Request request) throws InvalidRequestException, InvalidResponseException {
        Response response = null;
        LOGGER.info("Trying to find a service for request");
        for (DataProvider service : services) {
            LOGGER.debug("Trying to match request to {}", service);
            Response candidate = service.process(request);
            if (candidate != null) {
                LOGGER.info("Matched request to {}", service);
                response = candidate;
                break;
            }
        }
        if (response == null) {
            LOGGER.error("No service found to process request!");
            throw new InvalidRequestException(request);
        }
        return response;
    }
}
