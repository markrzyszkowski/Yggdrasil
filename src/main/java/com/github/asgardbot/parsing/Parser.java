package com.github.asgardbot.parsing;

import com.github.asgardbot.commons.Request;

public interface Parser {

    Request parse(String query);
    String getCommand();
}

