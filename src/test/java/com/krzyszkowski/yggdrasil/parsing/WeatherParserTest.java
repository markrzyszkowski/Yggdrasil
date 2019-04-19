package com.krzyszkowski.yggdrasil.parsing;

import com.krzyszkowski.yggdrasil.rqrs.WeatherRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherParserTest {

    @Test
    public void parse_correct_returnsDto() {
        WeatherRequest request = new WeatherParser().parse(new QueryDto().withQueryText("!weather test"));
        assertNotNull(request);
        assertEquals("test", request.getCity());
    }

    @Test
    public void parse_multiWordCity_returnsDto() {
        WeatherRequest request = new WeatherParser().parse(new QueryDto().withQueryText("!weather aaa bbb"));
        assertNotNull(request);
        assertEquals("aaa bbb", request.getCity());
    }

    @Test
    public void parse_wrongCommand_returnsNull() {
        WeatherRequest request = new WeatherParser().parse(new QueryDto().withQueryText("!test"));
        assertNull(request);
    }

    @Test
    public void parse_emptyQuery_returnsNull() {
        WeatherRequest request = new WeatherParser().parse(new QueryDto().withQueryText(""));
        assertNull(request);
    }

    @Test
    public void parse_tooFewArguments_returnsNull() {
        WeatherRequest request = new WeatherParser().parse(new QueryDto().withQueryText("!weather"));
        assertNull(request);
    }
}
