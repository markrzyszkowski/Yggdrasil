package com.github.yggdrasil.rqrs;

import com.github.yggdrasil.commons.Request;

public class WeatherRequest extends Request<WeatherRequest> {

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
