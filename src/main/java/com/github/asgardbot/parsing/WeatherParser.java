package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.WeatherRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WeatherParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(WeatherParser.class);

    @Override
    public WeatherRequest parse(String query) {
        LOGGER.debug("Attempting to parse {}", query);
        String[] tokens = query.split(" ");
        if (tokens.length == 2 && tokens[0].equalsIgnoreCase("!weather")) {
            LOGGER.info("Query matched");
            return new WeatherRequest().withCity(tokens[1]);
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!weather [location]";
    }
}
