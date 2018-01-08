package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.InvalidResponseException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.GoogleSearchRequest;
import com.github.asgardbot.rqrs.GoogleSearchResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoogleSearchDataProvider implements DataProvider {

    @Value("#{systemEnvironment['GOOGLE_KEY']}")
    private String apiKey;
    @Value("#{systemEnvironment['SEARCH_ENGINE']}")
    private String searchEngine;
    private Customsearch customSearch;
    private Logger LOGGER = LoggerFactory.getLogger(GoogleSearchDataProvider.class);

    public GoogleSearchDataProvider() {
        customSearch = new Customsearch.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
        }).setApplicationName("asgardbot").build();
    }

    @Override
    public GoogleSearchResponse process(Request request) throws InvalidResponseException {
        LOGGER.info("Attempting to process a request");
        LOGGER.debug(request.toString());
        GoogleSearchRequest rq = (GoogleSearchRequest)request;
        return getGoogleSearchResponse(rq);
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof GoogleSearchRequest;
    }

    private GoogleSearchResponse getGoogleSearchResponse(GoogleSearchRequest request) throws InvalidResponseException {
        LOGGER.info("Able to process request");
        try {
            Customsearch.Cse.List search = customSearch.cse().list(request.getQuery());
            search.setKey(apiKey)
                  .setCx(searchEngine)
                  .setSearchType(request.getType());
            List<Result> response = search.execute().getItems();
            return new GoogleSearchResponse().withResults(response);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new InvalidResponseException(null);
        }
    }
}
