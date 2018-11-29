package com.github.yggdrasil.dataproviders;

import com.github.yggdrasil.commons.Request;
import com.github.yggdrasil.commons.Response;

public interface DataProvider {

    Response process(Request request) throws Exception;
    boolean canProcess(Request request);
}
