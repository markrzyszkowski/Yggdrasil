package com.github.asgardbot.parsing;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.parsing.nlp.NlpParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class ParserImpl implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(ParserImpl.class);

    private Parser nlpParser;
    private Set<Parser> parsers;

    public ParserImpl(@Lazy Set<Parser> parsers,
                      @Lazy @Qualifier("nlp") Parser nlpParser) {
        this.nlpParser = nlpParser;
        this.parsers = parsers.stream()
                              .filter(e -> !(e instanceof ParserImpl || e instanceof NlpParser))
                              .collect(Collectors.toSet());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(this.parsers.toString());
        }
    }

    @Override
    public Request parse(QueryDto query) {
        LOGGER.info("Attempting to parse request text");
        LOGGER.debug(query.toString());
        for (Parser parser : parsers) {
            Request candidate = parser.parse(query);
            if (candidate != null) {
                return candidate;
            }
        }
        return nlpParser.parse(query);
    }
}
