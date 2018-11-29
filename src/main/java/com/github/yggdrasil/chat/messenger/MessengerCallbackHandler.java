package com.github.yggdrasil.chat.messenger;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.MessengerReceiveClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.messenger4j.MessengerPlatform.*;

@RestController
@RequestMapping("/messenger")
@ConditionalOnProperty({"MESSENGER_PAGE_ACCESS_TOKEN", "MESSENGER_APP_SECRET", "MESSENGER_VERIFY_TOKEN"})
public class MessengerCallbackHandler {

    private Logger LOGGER = LoggerFactory.getLogger(MessengerCallbackHandler.class);

    private String appSecret;
    private String verifyToken;
    private MessengerReceiveClient receiveClient;

    public MessengerCallbackHandler(EventHandlers eventHandlers, Environment environment) {
        this.appSecret = environment.getProperty("MESSENGER_APP_SECRET");
        this.verifyToken = environment.getProperty("MESSENGER_VERIFY_TOKEN");

        LOGGER.info("Initializing MessengerReceiveClient");
        this.receiveClient = MessengerPlatform.newReceiveClientBuilder(appSecret, verifyToken)
                                              .onTextMessageEvent(eventHandlers::handleTextMessageEvent)
                                              .onMessageDeliveredEvent(eventHandlers::handleMessageDeliveredEvent)
                                              .onMessageReadEvent(eventHandlers::handleMessageReadEvent)
                                              .fallbackEventHandler(eventHandlers::handleOtherEvent)
                                              .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> verifyWebhook(@RequestParam(MODE_REQUEST_PARAM_NAME) String mode,
                                                @RequestParam(VERIFY_TOKEN_REQUEST_PARAM_NAME) String verifyToken,
                                                @RequestParam(CHALLENGE_REQUEST_PARAM_NAME) String challenge) {
        LOGGER.info("Received Webhook verification request");
        try {
            return ResponseEntity.ok(receiveClient.verifyWebhook(mode, verifyToken, challenge));
        } catch (MessengerVerificationException e) {
            LOGGER.error("Webhook verification failed: '{}'", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handleCallback(@RequestBody String payload,
                                         @RequestHeader(SIGNATURE_HEADER_NAME) String signature) {
        LOGGER.info("Received Messenger callback");
        try {
            receiveClient.processCallbackPayload(payload, signature);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (MessengerVerificationException e) {
            LOGGER.error("Messenger callback processing failed: '{}'", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
