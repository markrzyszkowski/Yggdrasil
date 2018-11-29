package com.github.yggdrasil.chat.messenger;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.send.MessengerSendClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnProperty({"MESSENGER_PAGE_ACCESS_TOKEN", "MESSENGER_APP_SECRET", "MESSENGER_VERIFY_TOKEN"})
public class MessengerSendClientConfiguration {

    private Logger LOGGER = LoggerFactory.getLogger(MessengerSendClientConfiguration.class);

    private String pageAccessToken;

    public MessengerSendClientConfiguration(Environment environment) {
        this.pageAccessToken = environment.getProperty("MESSENGER_PAGE_ACCESS_TOKEN");
    }

    @Bean
    public MessengerSendClient messengerSendClient() {
        LOGGER.info("Initializing MessengerSendClient");
        return MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
    }
}
