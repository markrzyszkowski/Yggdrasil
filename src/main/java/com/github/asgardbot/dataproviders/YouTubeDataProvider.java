package com.github.asgardbot.dataproviders;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@ConditionalOnProperty("GOOGLE_KEY")
class YouTubeDataProvider implements DataProvider {

    @Value("#{systemEnvironment['GOOGLE_KEY']}")
    private String apiKey;
    private YouTube youTube;
    private Logger LOGGER = LoggerFactory.getLogger(YouTubeDataProvider.class);

    public YouTubeDataProvider() {
        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
        }).setApplicationName("asgardbot").build();
    }

    @Override
    public YouTubeResponse process(Request request) throws IOException {
        LOGGER.info("Attempting to process a request");
        LOGGER.debug(request.toString());
        YouTubeRequest rq = (YouTubeRequest)request;
        return getYouTubeResponse(rq);
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof YouTubeRequest;
    }

    private YouTubeResponse getYouTubeResponse(YouTubeRequest request) throws IOException {
        LOGGER.info("Able to process request");
            YouTube.Search.List search = youTube.search().list("id,snippet");
            search.setKey(apiKey)
                  .setQ(request.getQuery())
                  .setMaxResults(5L);
            List<SearchResult> response = search.execute().getItems();
            LOGGER.debug(response.toString());
            return new YouTubeResponse().withResults(response);
    }
}
