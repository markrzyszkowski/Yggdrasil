package com.github.asgardbot.dataproviders;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.rqrs.IdentityRequest;
import com.github.asgardbot.rqrs.IdentityResponse;
import org.springframework.stereotype.Component;

import static com.github.asgardbot.rqrs.IdentityResponse.fromIdRq;

@Component
class IdentityDataProvider implements DataProvider {

    @Override
    public IdentityResponse process(Request request)  {
        IdentityRequest identityRequest = (IdentityRequest)request;
        return fromIdRq(identityRequest);
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof IdentityRequest;
    }
}
