package com.github.yggdrasil.parsing.nlp;

import com.github.yggdrasil.commons.Request;
import com.github.yggdrasil.parsing.Parser;
import com.github.yggdrasil.parsing.QueryDto;
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

