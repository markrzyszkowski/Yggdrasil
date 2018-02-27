package com.github.asgardbot.chat.messenger;

import com.github.asgardbot.chat.ResponseDispatcher;
import com.github.asgardbot.commons.Response;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class MessengerResponseDispatcher implements ResponseDispatcher {

    private static final int MESSENGER_MESSAGE_MAX_LENGTH = 320;
    private Logger LOGGER = LoggerFactory.getLogger(MessengerResponseDispatcher.class);

    private MessengerSendClient sendClient;
    private List<String> awaitedResponses = new LinkedList<>();

    public MessengerResponseDispatcher(MessengerSendClient sendClient) {
        this.sendClient = sendClient;
    }

    @Override
    public void enqueueResponse(Response response) {
        String transactionId = response.getTransactionId();
        if (awaitedResponses.contains(transactionId)) {
            LOGGER.info("Response produced for '{}'", transactionId);
            awaitedResponses.remove(transactionId);
            List<String> responses = response.getMessages();
            LOGGER.debug("Response contents: '{}'", responses);

            responses.forEach(s -> sendTextMessage(transactionId, s));
        }
    }

    @Override
    public void awaitResponse(String transactionId) {
        LOGGER.info("Awaiting a response to '{}'", transactionId);
        awaitedResponses.add(transactionId);
    }

    private void sendTextMessage(String recipientId, String text) {
        try {
            Recipient recipient = Recipient.newBuilder().recipientId(recipientId).build();

            List<String> messages = splitResponseText(text);

            sendMessages(recipient, messages);
        } catch (MessengerApiException | MessengerIOException e) {
            LOGGER.error("Message could not be sent. An unexpected error occurred.", e);
        }
    }

    private void sendMessages(Recipient recipient,
                              List<String> messages) throws MessengerApiException, MessengerIOException {
        try {
            this.sendClient.sendSenderAction(recipient, NotificationType.NO_PUSH, SenderAction.TYPING_OFF);
        } catch (MessengerApiException | MessengerIOException e) {
            LOGGER.error("Could not set typing indicator to 'off'");
        }

        NotificationType notificationType = NotificationType.REGULAR;
        for (String message : messages) {
            this.sendClient.sendTextMessage(recipient, notificationType, message);
            notificationType = NotificationType.SILENT_PUSH;
        }
    }

    private List<String> splitResponseText(String text) {
        List<String> result = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            if (builder.length() + word.length() + 1 < MESSENGER_MESSAGE_MAX_LENGTH) {
                builder.append(" ").append(word);
            } else {
                result.add(builder.toString());
                builder = new StringBuilder().append(word);
            }
        }
        if (builder.length() > 0) {
            result.add(builder.toString());
        }
        return result;
    }
}
