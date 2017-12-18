package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.GoogleSearchRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class GoogleSearchParser implements Parser {

    @Override
    public GoogleSearchRequest parse(String query) {
        String[] tokens = query.split(" ");
        if (tokens.length >= 2 && (tokens[0].equalsIgnoreCase("!search") || tokens[0].equalsIgnoreCase("!imagesearch"))) {
            String text = Arrays.stream(tokens)
                                .skip(1)
                                .collect(Collectors.joining(" "));
            GoogleSearchRequest request = new GoogleSearchRequest().withQuery(text);
            return tokens[0].equalsIgnoreCase("!search") ? request : request.withType("image");
        }
        return null;
    }
}
