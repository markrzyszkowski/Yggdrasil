package com.krzyszkowski.yggdrasil.dataproviders;

import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.rqrs.IdentityRequest;
import com.krzyszkowski.yggdrasil.rqrs.IdentityResponse;
import org.springframework.stereotype.Component;

import static com.krzyszkowski.yggdrasil.rqrs.IdentityResponse.fromIdRq;

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
