package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;

public interface DataProvider {

    Response process(Request request) throws Exception;
    boolean canProcess(Request request);
}
