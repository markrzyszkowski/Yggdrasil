package com.github.asgardbot.nlp;

import com.github.asgardbot.commons.ServiceId;
import org.springframework.stereotype.Component;

@Component
public class NlpResolver {

    public ServiceId resolve(String payload) {
        return new ServiceId("placeholder");
    }
}
