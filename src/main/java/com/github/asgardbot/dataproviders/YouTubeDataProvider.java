package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.InvalidResponseException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.YouTubeRequest;
import com.github.asgardbot.rqrs.YouTubeResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class YouTubeDataProvider implements DataProvider {

    @Value("#{systemEnvironment['GOOGLE_KEY']}")
    private String apiKey;
    private YouTube youTube;
    private Logger LOGGER = LoggerFactory.getLogger(YouTubeDataProvider.class);

    public YouTubeDataProvider() {
        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
        }).setApplicationName("asgardbot").build();
    }

    @Override
    public YouTubeResponse process(Request request) throws InvalidResponseException {
        LOGGER.info("Attempting to process a request");
        LOGGER.debug(request.toString());
        if (request instanceof YouTubeRequest) {
            YouTubeRequest rq = (YouTubeRequest)request;
            return getYouTubeResponse(rq);
        }
        LOGGER.debug("Not able to process request");
        return null;
    }

    private YouTubeResponse getYouTubeResponse(YouTubeRequest request) throws InvalidResponseException {
        LOGGER.info("Able to process request");
        try {
            YouTube.Search.List search = youTube.search().list("id,snippet");
            search.setKey(apiKey)
                  .setType(request.getType())
                  .setQ(request.getQuery())
                  .setMaxResults(5L);
            List<SearchResult> response = search.execute().getItems();
            return new YouTubeResponse().withResults(response);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new InvalidResponseException(null);
        }
    }
}
