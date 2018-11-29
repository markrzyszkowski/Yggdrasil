package com.github.yggdrasil.chat.messenger;

import com.github.yggdrasil.chat.RequestReceiver;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.receive.events.FallbackEvent;
import com.github.messenger4j.receive.events.MessageDeliveredEvent;
import com.github.messenger4j.receive.events.MessageReadEvent;
import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty({"MESSENGER_PAGE_ACCESS_TOKEN", "MESSENGER_APP_SECRET", "MESSENGER_VERIFY_TOKEN"})
public class EventHandlers {

    private Logger LOGGER = LoggerFactory.getLogger(EventHandlers.class);

    private MessengerResponseDispatcher responseDispatcher;
    private MessengerSendClient sendClient;
    private RequestReceiver requestReceiver;

    public EventHandlers(MessengerResponseDispatcher responseDispatcher,
                         MessengerSendClient sendClient,
                         RequestReceiver requestReceiver) {
        this.responseDispatcher = responseDispatcher;
        this.sendClient = sendClient;
        this.requestReceiver = requestReceiver;
    }

    public void handleTextMessageEvent(TextMessageEvent event) {
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

        requestReceiver.handleMessage(senderId, messageText, responseDispatcher);
    }

    public void handleMessageDeliveredEvent(MessageDeliveredEvent event) {
            LOGGER.info("Received MessageDeliveredEvent");
            List<String> messageIds = event.getMids();
            String senderId = event.getSender().getId();

            if (messageIds != null) {
                messageIds.forEach(messageId -> LOGGER.debug("Received delivery confirmation for message '{}'",
                                                             messageId));
            }
            LOGGER.info("All messages were delivered to user '{}'", senderId);
    }

    public void handleMessageReadEvent(MessageReadEvent event) {
            LOGGER.info("Received MessageReadEvent");
            String senderId = event.getSender().getId();

            LOGGER.info("All messages were read by user '{}'", senderId);
    }

    public void handleOtherEvent(FallbackEvent event) {
            LOGGER.info("Received FallbackEvent");
            String senderId = event.getSender().getId();
            LOGGER.debug("Received unsupported message from user '{}': '{}'", senderId, event);
    }
}
