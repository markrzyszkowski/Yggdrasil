package com.github.asgardbot.parsing;

import com.github.asgardbot.commons.Request;

public interface Parser {

    Request parse(QueryDto query);
    default String getCommand(){
        return null;
    }
}

