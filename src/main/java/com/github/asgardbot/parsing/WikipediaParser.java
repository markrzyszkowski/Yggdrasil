package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.WikipediaRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class WikipediaParser implements Parser {

    @Override
    public WikipediaRequest parse(String query) {
        String[] tokens = query.split(" ");
        if (tokens.length >= 2 && tokens[0].equalsIgnoreCase("!wiki")) {
            final String title = Arrays.stream(tokens)
                                       .skip(1)
                                       .collect(Collectors.joining(" "));
            return new WikipediaRequest().withQuery(title);
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!wiki [query]";
    }
}
