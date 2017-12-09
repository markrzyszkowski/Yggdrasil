package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WeatherResponse extends Response {

    private Map<String, String> data = new HashMap<>();

    public WeatherResponse withDatum(String key, String value) {
        data.put(key, value);
        return this;
    }

    @Override
    public String getResponseText() {
        return data.entrySet().stream()
                   .map(e -> String.format("%s: %s", e.getKey(), e.getValue()))
                   .collect(Collectors.joining(" "));
    }
}