package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.UppercaseRequest;
import org.springframework.stereotype.Component;

@Component
public class UppercaseParser implements Parser {

    @Override
    public UppercaseRequest parse(String query) {
        String[] tokens = query.split(" ");
        if (tokens.length == 2 && tokens[0].equalsIgnoreCase("!upper")) {
            String phrase = tokens[1];
            return new UppercaseRequest().withValue(phrase);
        }
        return null;
    }
}
