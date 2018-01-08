package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.TranslationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class TranslationParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(TranslationParser.class);

    @Override
    public TranslationRequest parse(String query) {
        LOGGER.debug("Attempting to parse {}", query);
        String[] tokens = query.split(" ");
        if (tokens.length >= 4 && tokens[0].equalsIgnoreCase("!translate")) {
            LOGGER.info("Query matched");
            String text = Arrays.stream(tokens)
                                .skip(3)
                                .collect(Collectors.joining(" "));
            return new TranslationRequest().withDirection(tokens[1] + "-" + tokens[2]).withText(text);
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!translate [from_lang] [to_lang] [phrase]";
    }
}
