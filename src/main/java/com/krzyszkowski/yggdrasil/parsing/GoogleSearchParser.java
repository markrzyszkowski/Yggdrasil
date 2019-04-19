package com.krzyszkowski.yggdrasil.parsing;

import com.krzyszkowski.yggdrasil.rqrs.GoogleSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty({"GOOGLE_KEY", "SEARCH_ENGINE"})
class GoogleSearchParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(GoogleSearchParser.class);

    @Override
    public GoogleSearchRequest parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        String[] tokens = query.getQueryText().split(" ");
        if (tokens.length >= 2 && (tokens[0].equalsIgnoreCase("!search")
                                   || tokens[0].equalsIgnoreCase("!imagesearch"))) {
            LOGGER.info("Query matched");
            String text = Arrays.stream(tokens)
                                .skip(1)
                                .collect(Collectors.joining(" "));
            GoogleSearchRequest request = new GoogleSearchRequest().withQuery(text);
            return tokens[0].equalsIgnoreCase("!search") ? request : request.withType("image");
        }
        return null;
    }

    @Override
    public String getCommand() {
        return String.format("!search [query]%n!imagesearch [query]");
    }
}
