package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.WikipediaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class WikipediaParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(WikipediaParser.class);

    @Override
    public WikipediaRequest parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        String[] tokens = query.getQueryText().split(" ");
        if (tokens.length >= 2 && tokens[0].equalsIgnoreCase("!wiki")) {
            LOGGER.info("Query matched");
            final String title = Arrays.stream(tokens)
                                       .skip(1)
                                       .collect(Collectors.joining(" "));
            return new WikipediaRequest().withQuery(title);
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!wiki [query]";
    }
}
