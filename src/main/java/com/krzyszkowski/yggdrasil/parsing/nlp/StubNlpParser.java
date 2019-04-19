package com.krzyszkowski.yggdrasil.parsing.nlp;

import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.parsing.Parser;
import com.krzyszkowski.yggdrasil.parsing.QueryDto;
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

