package com.github.yggdrasil.parsing;

import com.github.yggdrasil.rqrs.UppercaseRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UppercaseParserTest {

    @Test
    public void parse_correct_returnsDto() {
        UppercaseRequest request = new UppercaseParser().parse(new QueryDto().withQueryText("!upper test"));
        assertNotNull(request);
        assertEquals("test", request.getValue());
    }

    @Test
    public void parse_tooManyArguments_returnsNull() {
        UppercaseRequest request = new UppercaseParser().parse(new QueryDto().withQueryText("!upper aaa bbb"));
        assertNull(request);
    }

    @Test
    public void parse_wrongCommand_returnsNull() {
        UppercaseRequest request = new UppercaseParser().parse(new QueryDto().withQueryText("!test"));
        assertNull(request);
    }

    @Test
    public void parse_emptyQuery_returnsNull() {
        UppercaseRequest request = new UppercaseParser().parse(new QueryDto().withQueryText(""));
        assertNull(request);
    }

    @Test
    public void parse_tooFewArguments_returnsNull() {
        UppercaseRequest request = new UppercaseParser().parse(new QueryDto().withQueryText("!upper"));
        assertNull(request);
    }
}
