package com.krzyszkowski.yggdrasil.dataproviders;

import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.rqrs.WikipediaRequest;
import com.krzyszkowski.yggdrasil.rqrs.WikipediaResponse;
import fastily.jwiki.core.Wiki;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
class WikipediaDataProvider implements DataProvider {

    private final Wiki wiki;
    private Logger LOGGER = LoggerFactory.getLogger(WikipediaDataProvider.class);

    public WikipediaDataProvider() {
        wiki = new Wiki("en.wikipedia.org");
    }

    @Override
    public WikipediaResponse process(Request request) {
        LOGGER.info("Attempting to process a request");
        LOGGER.debug(request.toString());
        WikipediaRequest rq = (WikipediaRequest)request;
        return getWikipediaResponse(rq);
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof WikipediaRequest;
    }

    private WikipediaResponse getWikipediaResponse(WikipediaRequest request) {
        LOGGER.info("Able to process request");
        String response = wiki.getTextExtract(request.getQuery());
        LOGGER.debug("Wikipedia responded with: '{}'", response);
        String excerpt = response != null
                         ? getSomeSentences(response, 15)
                         : String.format("Article with title %s does not exist", request.getQuery());
        return new WikipediaResponse().withText(excerpt);
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
