package com.github.asgardbot.dataproviders;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@ConditionalOnProperty({"GOOGLE_KEY", "SEARCH_ENGINE"})
class GoogleSearchDataProvider implements DataProvider {

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
    public GoogleSearchResponse process(Request request) throws IOException {
        LOGGER.info("Attempting to process a request");
        LOGGER.debug(request.toString());
        GoogleSearchRequest rq = (GoogleSearchRequest)request;
        return getGoogleSearchResponse(rq);
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof GoogleSearchRequest;
    }

    private GoogleSearchResponse getGoogleSearchResponse(GoogleSearchRequest request) throws IOException {
        LOGGER.info("Able to process request");
        Customsearch.Cse.List search = customSearch.cse().list(request.getQuery());
        search.setKey(apiKey)
              .setCx(searchEngine)
              .setSearchType(request.getType());
        List<Result> response = search.execute().getItems();
        LOGGER.debug(response.toString());
        return new GoogleSearchResponse().withResults(response);
    }
}
