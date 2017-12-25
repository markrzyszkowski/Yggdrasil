package com.github.asgardbot.rqrs;

import com.github.asgardbot.commons.Request;

public class WeatherRequest extends Request {

    private String city;

    public WeatherRequest withCity(String city) {
        this.city = city;
        return this;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "WeatherRequest{" +
               "city='" + city + '\'' +
               ", transactionId='" + transactionId + '\'' +
               '}';
    }
}
