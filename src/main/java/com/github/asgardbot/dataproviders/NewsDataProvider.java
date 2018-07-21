package com.github.asgardbot.dataproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;
import com.github.asgardbot.rqrs.NewsRequest;
import com.github.asgardbot.rqrs.NewsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty("NEWSAPI_KEY")
class NewsDataProvider extends AbstractRestfulDataProvider {

    @Value("#{systemEnvironment['NEWSAPI_KEY']}")
    private String apiKey;
    @Value("${dataproviders.newsapi.endpoint}")
    private String endpointUrl;
    private Logger LOGGER = LoggerFactory.getLogger(NewsDataProvider.class);

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof NewsRequest;
    }

    @Override
    protected String prepareRequest(Request request) {
        return String.format("%s?language=en&apiKey=%s", endpointUrl, apiKey);
    }

    @Override
    protected Response processResponse(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        ArrayNode articles = (ArrayNode)root.get("articles");
        NewsResponse newsResponse = new NewsResponse();
        for (int i = 0; i < 10; i++) {
            JsonNode article = articles.get(i);
            JsonNode sourceName = article.get("source").get("name");
            JsonNode title = article.get("title");
            JsonNode url = article.get("url");
            newsResponse.withHeadline(String.format("%s - %s: %s",
                                                    sourceName.textValue(),
                                                    title.textValue(),
                                                    url.textValue()));
        }
        return newsResponse;
    }
}
