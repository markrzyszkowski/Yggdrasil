package com.github.asgardbot.chat.messenger;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.core.MessageDispatcher;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.parsing.QueryDto;
import com.github.asgardbot.rqrs.ErrorResponse;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.github.messenger4j.receive.handlers.MessageDeliveredEventHandler;
import com.github.messenger4j.receive.handlers.MessageReadEventHandler;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventHandlers {

    private Logger LOGGER = LoggerFactory.getLogger(EventHandlers.class);

    private MessageDispatcher router;
    private MessengerResponseDispatcher responseDispatcher;
    private Parser parser;
    private MessengerSendClient sendClient;

    public EventHandlers(MessageDispatcher router,
                         MessengerResponseDispatcher responseDispatcher,
                         Parser parser,
                         MessengerSendClient sendClient) {
        this.router = router;
        this.responseDispatcher = responseDispatcher;
        this.parser = parser;
        this.sendClient = sendClient;
    }

    public TextMessageEventHandler getTextMessageEventHandler() {
        return event -> {
            LOGGER.info("Received TextMessageEvent");
            String messageId = event.getMid();
            String messageText = event.getText();
            String senderId = event.getSender().getId();
            LOGGER.debug("Received message '{}' from user '{}' with text '{}'", messageId, senderId, messageText);

            Recipient recipient = Recipient.newBuilder().recipientId(senderId).build();
            try {
                this.sendClient.sendSenderAction(recipient, NotificationType.NO_PUSH, SenderAction.MARK_SEEN);
            } catch (MessengerApiException | MessengerIOException e) {
                LOGGER.error("Could not mark received message as seen");
            }
            try {
                this.sendClient.sendSenderAction(recipient, NotificationType.NO_PUSH, SenderAction.TYPING_ON);
            } catch (MessengerApiException | MessengerIOException e) {
                LOGGER.error("Could not set typing indicator to 'on'");
            }

            Request request = parser.parse(new QueryDto().withQueryText(messageText).withSessionId(senderId));
            responseDispatcher.awaitResponse(senderId);
            if (request != null) {
                router.enqueueRequest(request.withTransactionId(senderId));
            } else {
                LOGGER.error("Failed to parse query '{}'", messageText);
                responseDispatcher.enqueueResponse(new ErrorResponse("Something went wrong", null)
                                                           .withTransactionId(senderId));
            }
        };
    }

    public MessageDeliveredEventHandler getMessageDeliveredEventHandler() {
        return event -> {
            LOGGER.info("Received MessageDeliveredEvent");
            List<String> messageIds = event.getMids();
            String senderId = event.getSender().getId();

            if (messageIds != null) {
                messageIds.forEach(messageId -> LOGGER.debug("Received delivery confirmation for message '{}'",
                                                             messageId));
            }
            LOGGER.info("All messages were delivered to user '{}'", senderId);
        };
    }

    public MessageReadEventHandler getMessageReadEventHandler() {
        return event -> {
            LOGGER.info("Received MessageReadEvent");
            String senderId = event.getSender().getId();

            LOGGER.info("All messages were read by user '{}'", senderId);
        };
    }

    public FallbackEventHandler getFallbackEventHandler() {
        return event -> {
            LOGGER.info("Received FallbackEvent");
            String senderId = event.getSender().getId();
            LOGGER.debug("Received unsupported message from user '{}': '{}'", senderId, event);
        };
    }
}
