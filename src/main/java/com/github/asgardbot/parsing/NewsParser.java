package com.github.asgardbot.parsing;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.NewsRequest;
import org.springframework.stereotype.Component;

@Component
public class NewsParser implements Parser {

    @Override
    public Request parse(String query) {
        String[] tokens = query.split(" ");
        if (tokens.length == 1 && tokens[0].equalsIgnoreCase("!news")) {
            return new NewsRequest();
        }
        return null;
    }
}
