package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.*;

public interface DataProvider {

    ServiceId getServiceId();

    Response process(Request request) throws InvalidRequestException, InvalidResponseException;
}
