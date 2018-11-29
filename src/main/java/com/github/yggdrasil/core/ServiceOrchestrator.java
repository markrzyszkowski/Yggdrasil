package com.github.yggdrasil.core;

import com.github.yggdrasil.commons.InvalidRequestException;
import com.github.yggdrasil.commons.InvalidResponseException;
import com.github.yggdrasil.commons.Request;
import com.github.yggdrasil.commons.Response;
import com.github.yggdrasil.dataproviders.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("orchestrator")
class ServiceOrchestrator implements DataProvider {

    private Logger LOGGER = LoggerFactory.getLogger(ServiceOrchestrator.class);
    private List<DataProvider> services;

    public ServiceOrchestrator(List<DataProvider> services) {
        this.services = services;
    }

    @Override
    public Response process(Request request) throws InvalidRequestException, InvalidResponseException {
        DataProvider service = findServiceForRequest(request);
        LOGGER.debug("Matched request to '{}'", service);
        return invoke(service, request);
    }

    private DataProvider findServiceForRequest(Request request) throws InvalidRequestException {
        return services.stream()
                       .filter(e -> e.canProcess(request))
                       .findAny()
                       .orElseThrow(() -> new InvalidRequestException(request));
    }

    private Response invoke(DataProvider service, Request request) throws InvalidResponseException {
        try {
            return service.process(request);
        } catch (Exception e) {
            throw new InvalidResponseException();
        }
    }

    @Override
    public boolean canProcess(Request request) {
        return true;
    }
}
