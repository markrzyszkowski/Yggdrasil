package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.UppercaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UppercaseParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(UppercaseParser.class);

    @Override
    public UppercaseRequest parse(String query) {
        LOGGER.debug("Attempting to parse {}", query);
        String[] tokens = query.split(" ");
        if (tokens.length == 2 && tokens[0].equalsIgnoreCase("!upper")) {
            LOGGER.info("Query matched");
            String phrase = tokens[1];
            return new UppercaseRequest().withValue(phrase);
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!upper [text]";
    }
}
