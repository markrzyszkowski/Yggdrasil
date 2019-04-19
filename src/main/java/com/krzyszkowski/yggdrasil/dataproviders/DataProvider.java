package com.krzyszkowski.yggdrasil.dataproviders;

import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.commons.Response;

public interface DataProvider {

    Response process(Request request) throws Exception;
    boolean canProcess(Request request);
}
