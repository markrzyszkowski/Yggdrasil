package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeatherResponse extends Response {

    private Map<String, String> data = new HashMap<>();

    public WeatherResponse withDatum(String key, String value) {
        data.put(key, value);
        return this;
    }

    @Override
    public List<String> getMessages() {
        return List.of(data.entrySet().stream()
                           .map(e -> String.format("%s: %s", e.getKey(), e.getValue()))
                           .collect(Collectors.joining(" ")));
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
               "data=" + data +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
