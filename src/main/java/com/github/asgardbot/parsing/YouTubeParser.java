package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.YouTubeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class YouTubeParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(YouTubeParser.class);

    @Override
    public YouTubeRequest parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        String[] tokens = query.getQueryText().split(" ");
        if (tokens.length >= 2 && tokens[0].equalsIgnoreCase("!youtube")) {
            LOGGER.info("Query matched");
            String text = Arrays.stream(tokens).skip(1).collect(Collectors.joining(" "));
            return new YouTubeRequest().withQuery(text);
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!youtube [video|channel|playlist] [name]";
    }
}
