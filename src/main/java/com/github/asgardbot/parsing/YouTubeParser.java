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
    public YouTubeRequest parse(String query) {
        LOGGER.debug("Attempting to parse {}", query);
        String[] tokens = query.split(" ");
        if (tokens.length >= 3 && tokens[0].equalsIgnoreCase("!youtube")) {
            LOGGER.info("Query matched");
            if (tokens[1].equalsIgnoreCase("video")
                || tokens[1].equalsIgnoreCase("channel")
                || tokens[1].equalsIgnoreCase("playlist")) {
                String text = Arrays.stream(tokens)
                                    .skip(2)
                                    .collect(Collectors.joining(" "));
                return new YouTubeRequest().withType(tokens[1].toLowerCase()).withQuery(text);
            }
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!youtube [video|channel|playlist] [name]";
    }
}
