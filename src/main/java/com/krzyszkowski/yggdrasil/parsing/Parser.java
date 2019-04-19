package com.krzyszkowski.yggdrasil.parsing;

import com.krzyszkowski.yggdrasil.commons.Request;

public interface Parser {

    Request parse(QueryDto query);
    default String getCommand(){
        return null;
    }
}

