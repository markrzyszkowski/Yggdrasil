package com.github.yggdrasil.dataproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yggdrasil.commons.Request;
import com.github.yggdrasil.rqrs.WeatherRequest;
import com.github.yggdrasil.rqrs.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty("OPEN_WEATHER_MAP_KEY")
class WeatherDataProvider extends AbstractRestfulDataProvider {

    @Value("#{systemEnvironment['OPEN_WEATHER_MAP_KEY']}")
    private String apiKey;
    @Value("${dataproviders.openweathermap.endpoint}")
    private String endpointUrl;
    private Logger LOGGER = LoggerFactory.getLogger(WeatherDataProvider.class);

    @Override
    public boolean canProcess(Request request) {
        return request instanceof WeatherRequest;
    }

    @Override
    protected String prepareRequest(Request request) {
        WeatherRequest weatherRequest = (WeatherRequest)request;
        return String.format("%s?appid=%s&q=%s&units=metric",
                             endpointUrl, apiKey,
                             weatherRequest.getCity());
    }

    @Override
    protected WeatherResponse processResponse(String rs) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(rs);
        JsonNode weather = root.get("weather").get(0);
        JsonNode main = root.get("main");
        JsonNode wind = root.get("wind");
        JsonNode clouds = root.get("clouds");
        JsonNode rain = root.get("rain");
        JsonNode snow = root.get("snow");

        WeatherResponse response = new WeatherResponse()
          .withDatum("Weather", weather.get("main").textValue())
          .withDatum("Description", weather.get("description").textValue())
          .withDatum("Temperature", main.get("temp").doubleValue() + "°C")
          .withDatum("Air pressure", main.get("pressure").doubleValue() + " hPa")
          .withDatum("Humidity", main.get("humidity").doubleValue() + "%")
          .withDatum("Wind speed", wind.get("speed").doubleValue() + " m/s")
          .withDatum("Cloud cover", clouds.get("all").doubleValue() + "%");

        if (rain != null) {
            response.withDatum("rain", rain.get("3h").doubleValue() + " mm");
        }

        if (snow != null) {
            response.withDatum("snow", snow.get("3h").doubleValue() + " mm");
        }

        return response;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
