package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.*;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestfulDataProvider implements DataProvider {

    protected abstract Logger getLogger();

    public abstract boolean canProcess(Request request);

    protected abstract String prepareRequest(Request request) throws InvalidRequestException;

    protected abstract Response processResponse(String response) throws InvalidResponseException;

    private String communicate(String request) {
        RestTemplate restTemplate = new RestTemplate();

        getLogger().info("Attempting to communicate with data provider");

        ResponseEntity<String> response = restTemplate.getForEntity(request, String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new ExternalApiException(response.getStatusCode(), request);
        }
        return response.getBody();
    }

    @Override
    public Response process(Request request) throws InvalidRequestException, InvalidResponseException {
        getLogger().info("Attempting to process a request");
        getLogger().debug(request.toString());

        String preparedRequest = prepareRequest(request);
        getLogger().debug("Prepared request: {}", preparedRequest);

        String response = communicate(preparedRequest);
        getLogger().debug("Retrieved response: {}", response);

        return processResponse(response);
    }
}
