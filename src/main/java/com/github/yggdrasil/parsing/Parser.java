package com.github.yggdrasil.parsing;

import com.github.yggdrasil.commons.Request;

public interface Parser {

    Request parse(QueryDto query);
    default String getCommand(){
        return null;
    }
}

