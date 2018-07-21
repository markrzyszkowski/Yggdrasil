package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.WeatherRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("OPEN_WEATHER_MAP_KEY")
class WeatherParser implements Parser {

    private Logger LOGGER = LoggerFactory.getLogger(WeatherParser.class);

    @Override
    public WeatherRequest parse(QueryDto query) {
        LOGGER.debug("Attempting to parse '{}'", query);
        String[] tokens = query.getQueryText().split(" ");
        if (tokens.length >= 2 && tokens[0].equalsIgnoreCase("!weather")) {
            LOGGER.info("Query matched");
            return new WeatherRequest().withCity(query.getQueryText().substring(9));
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "!weather [location]";
    }
}
