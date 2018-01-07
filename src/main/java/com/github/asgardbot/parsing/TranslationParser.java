package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.TranslationRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class TranslationParser implements Parser {

    @Override
    public TranslationRequest parse(String query) {
        String[] tokens = query.split(" ");
        if (tokens.length >= 4 && tokens[0].equalsIgnoreCase("!translate")) {
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
