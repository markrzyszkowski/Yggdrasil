package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.InvalidResponseException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.WikipediaRequest;
import com.github.asgardbot.rqrs.WikipediaResponse;
import fastily.jwiki.core.Wiki;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
public class WikipediaDataProvider implements DataProvider {

    private final Wiki wiki;
    private Logger LOGGER = LoggerFactory.getLogger(WikipediaDataProvider.class);

    public WikipediaDataProvider() {
        wiki = new Wiki("en.wikipedia.org");
    }

    @Override
    public WikipediaResponse process(Request request) throws InvalidResponseException {
        LOGGER.info("Attempting to process a request");
        LOGGER.debug(request.toString());
        WikipediaRequest rq = (WikipediaRequest)request;
        return getWikipediaResponse(rq);
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof WikipediaRequest;
    }

    private WikipediaResponse getWikipediaResponse(WikipediaRequest request) throws InvalidResponseException {
        LOGGER.info("Able to process request");
        try {
            String response = wiki.getTextExtract(request.getQuery());
            LOGGER.debug(response);
            String excerpt = response != null
                             ? getSomeSentences(response, 15)
                             : String.format("Article with title %s does not exist", request.getQuery());
            return new WikipediaResponse().withText(excerpt);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new InvalidResponseException(null);
        }
    }

    private String getSomeSentences(String response, int n) {
        StringTokenizer tokenizer = new StringTokenizer(response, ".");
        StringBuilder builder = new StringBuilder();
        int count = 0;
        while (tokenizer.hasMoreTokens() && count < n) {
            builder.append(tokenizer.nextToken()).append('.');
            count++;
        }
        return builder.toString();
    }
}
