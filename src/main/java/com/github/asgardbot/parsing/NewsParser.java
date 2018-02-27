package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.NewsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NewsParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(NewsParser.class);

    @Override
    public NewsRequest parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        String[] tokens = query.getQueryText().split(" ");
        if (tokens.length == 1 && tokens[0].equalsIgnoreCase("!news")) {
            LOGGER.info("Query matched");
            return new NewsRequest();
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!news";
    }
}
