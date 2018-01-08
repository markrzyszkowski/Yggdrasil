package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.InvalidResponseException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;

public interface DataProvider {

    Response process(Request request) throws InvalidRequestException, InvalidResponseException;
    boolean canProcess(Request request);
}
