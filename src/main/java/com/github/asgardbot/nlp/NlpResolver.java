package com.github.asgardbot.nlp;

import com.github.asgardbot.commons.ServiceId;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NlpResolver {

    Map<String, ServiceId> commands;

    public ServiceId resolve(String payload) {
        return new ServiceId("placeholder");
    }
}
