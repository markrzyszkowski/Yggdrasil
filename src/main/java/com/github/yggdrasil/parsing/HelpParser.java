package com.github.yggdrasil.parsing;

import com.github.yggdrasil.commons.Request;
import com.github.yggdrasil.rqrs.HelpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class HelpParser implements Parser {

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
