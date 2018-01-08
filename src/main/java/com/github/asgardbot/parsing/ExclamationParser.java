package com.github.asgardbot.parsing;

import com.github.asgardbot.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class ExclamationParser implements Parser {

    Logger LOGGER = LoggerFactory.getLogger(ExclamationParser.class);

    private NlpParser nlpParser;
    private Set<Parser> parsers;

    public ExclamationParser(NlpParser nlpParser, Set<Parser> parsers) {
        this.nlpParser = nlpParser;
        this.parsers = parsers.stream()
                              .filter(e -> !(e instanceof ExclamationParser || e instanceof NlpParser))
                              .collect(Collectors.toSet());
    }

    @Override
    public Request parse(String query) {
        LOGGER.info("Attempting to parse request text");
        LOGGER.debug(query);
        for (Parser parser : parsers) {
            Request candidate = parser.parse(query);
            if (candidate != null) {
                return candidate;
            }
        }
        return nlpParser.parse(query);
    }

    @Override
    public String getCommand() {
        return null;
    }
}
