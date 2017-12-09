package com.github.asgardbot.parsing;

import com.github.asgardbot.rqrs.WeatherRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class WeatherParserTest {

    @Test
    public void parse_correct_returnsDto() {
        WeatherRequest request = new WeatherParser().parse("!weather test");
        assertNotNull(request);
        assertEquals("test", request.getCity());
    }

    @Test
    public void parse_tooManyArguments_returnsNull() {
        WeatherRequest request = new WeatherParser().parse("!weather aaa bbb");
        assertNull(request);
    }

    @Test
    public void parse_wrongCommand_returnsNull() {
        WeatherRequest request = new WeatherParser().parse("!test");
        assertNull(request);
    }

    @Test
    public void parse_emptyQuery_returnsNull() {
        WeatherRequest request = new WeatherParser().parse("");
        assertNull(request);
    }

    @Test
    public void parse_tooFewArguments_returnsNull() {
        WeatherRequest request = new WeatherParser().parse("!weather");
        assertNull(request);
    }

}
