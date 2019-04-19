package com.krzyszkowski.yggdrasil.dataproviders;

import com.krzyszkowski.yggdrasil.commons.ExternalApiException;
import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.commons.Response;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

abstract class AbstractRestfulDataProvider implements DataProvider {

    protected abstract Logger getLogger();

    public abstract boolean canProcess(Request request);

    @Override
    public Response process(Request request) throws Exception {
        getLogger().info("Attempting to process a request");
        getLogger().debug(request.toString());

        String preparedRequest = prepareRequest(request);
        getLogger().debug("Prepared request: '{}'", preparedRequest);

        String response = communicate(preparedRequest);
        getLogger().debug("Retrieved response: '{}'", response);

        return processResponse(response);
    }

    protected abstract String prepareRequest(Request request) throws Exception;

    private String communicate(String request) {
        RestTemplate restTemplate = newRestTemplate();

        getLogger().info("Attempting to communicate with data provider");

        ResponseEntity<String> response = restTemplate.getForEntity(request, String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new ExternalApiException(response.getStatusCode(), request);
        }
        return response.getBody();
    }

    private RestTemplate newRestTemplate() {
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return rt;
    }

    protected abstract Response processResponse(String response) throws Exception;
}
