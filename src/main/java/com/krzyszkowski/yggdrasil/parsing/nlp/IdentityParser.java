package com.krzyszkowski.yggdrasil.parsing.nlp;

import com.krzyszkowski.yggdrasil.parsing.Parser;
import com.krzyszkowski.yggdrasil.parsing.QueryDto;
import com.krzyszkowski.yggdrasil.rqrs.IdentityRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class IdentityParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(IdentityParser.class);

    @Override
    public IdentityRequest parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        final String queryText = query.getQueryText();
        String[] tokens = queryText.split(" ");
        if (tokens.length >= 2 && tokens[0].equalsIgnoreCase("!id")) {
            LOGGER.info("Query matched");
            return new IdentityRequest().withText(queryText.substring(4));
        }
        return null;
    }
}
