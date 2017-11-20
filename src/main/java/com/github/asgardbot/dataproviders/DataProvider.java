package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;
import com.github.asgardbot.commons.ServiceId;

public interface DataProvider {

    Response process(Request request) throws InvalidRequestException;

    ServiceId getServiceId();
}
