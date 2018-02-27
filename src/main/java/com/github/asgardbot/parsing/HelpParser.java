package com.github.asgardbot.parsing;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.HelpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelpParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(HelpParser.class);

    @Override
    public Request parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        if (query.getQueryText().equalsIgnoreCase("!help")) {
            LOGGER.info("Query matched");
            return new HelpRequest();
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!help";
    }
}
