package com.github.yggdrasil.dataproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.yggdrasil.commons.Request;
import com.github.yggdrasil.commons.Response;
import com.github.yggdrasil.rqrs.TranslationRequest;
import com.github.yggdrasil.rqrs.TranslationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty("TRANSLATE_KEY")
class TranslationDataProvider extends AbstractRestfulDataProvider {

    @Value("#{systemEnvironment['TRANSLATE_KEY']}")
    private String apiKey;
    @Value("${dataproviders.translate.endpoint}")
    private String endpointUrl;
    private Logger LOGGER = LoggerFactory.getLogger(TranslationDataProvider.class);

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public boolean canProcess(Request request) {
        return request instanceof TranslationRequest;
    }

    @Override
    protected String prepareRequest(Request request) {
        TranslationRequest translationRequest = (TranslationRequest)request;
        return String.format("%s?key=%s&text=%s&lang=%s",
                             endpointUrl,
                             apiKey,
                             translationRequest.getText(),
                             translationRequest.getDirection());
    }

    @Override
    protected Response processResponse(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        ArrayNode texts = (ArrayNode)root.get("text");
        JsonNode text = texts.get(0);
        return new TranslationResponse().withText(text.textValue());
    }
}
