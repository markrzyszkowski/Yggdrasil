package com.github.asgardbot.parsing.nlp;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.parsing.QueryDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Qualifier("nlp")
@Conditional(ConditionalOnMissingDialogflowCredentials.class)
public class StubNlpParser implements Parser {

    @Override
    public Request parse(QueryDto query) {
        return null;
    }
}

