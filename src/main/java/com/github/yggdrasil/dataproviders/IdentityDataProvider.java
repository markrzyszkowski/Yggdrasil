package com.github.yggdrasil.dataproviders;

import com.github.yggdrasil.commons.Request;
import com.github.yggdrasil.rqrs.IdentityRequest;
import com.github.yggdrasil.rqrs.IdentityResponse;
import org.springframework.stereotype.Component;

import static com.github.yggdrasil.rqrs.IdentityResponse.fromIdRq;

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
