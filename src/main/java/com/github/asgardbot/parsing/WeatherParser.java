package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.WeatherRequest;
import org.springframework.stereotype.Component;

@Component
public class WeatherParser implements Parser {

    @Override
    public WeatherRequest parse(String query) {
        String[] tokens = query.split(" ");
        if (tokens[0].equals("!weather")) {
            return new WeatherRequest().withCity(tokens[1]);
        }
        return null;
    }
}
