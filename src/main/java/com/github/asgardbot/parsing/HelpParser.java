package com.github.asgardbot.parsing;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.HelpRequest;
import org.springframework.stereotype.Component;

@Component
public class HelpParser implements Parser {

    @Override
    public Request parse(String query) {
        if (query.equalsIgnoreCase("!help")) {
            return new HelpRequest();
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!help";
    }
}
