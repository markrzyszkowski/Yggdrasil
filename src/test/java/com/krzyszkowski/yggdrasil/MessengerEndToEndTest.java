package com.krzyszkowski.yggdrasil;

import com.krzyszkowski.yggdrasil.chat.messenger.EventHandlers;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.send.MessengerSendClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class MessengerEndToEndTest {

    @Autowired
    private EventHandlers eventHandlers;

    @MockBean
    private MessengerSendClient client;

    @Before
    public void setUp() throws MessengerApiException, MessengerIOException {
        doReturn(null).when(client).sendSenderAction(any(), any());
        doReturn(null).when(client).sendTextMessage(any(), any(), anyString());
    }

    @Test
    public void endToEnd_onMessengerRequest_shouldReturnResponse()
    throws MessengerApiException, MessengerIOException, InterruptedException {
        TextMessageEvent event = new TextMessageEvent("testSender",
                                                      "testRecipient",
                                                      Date.from(Instant.now()),
                                                      "testMid",
                                                      "!help");
        eventHandlers.handleTextMessageEvent(event);
        Thread.sleep(250);
        verify(client, times(1)).sendTextMessage(any(), any(), anyString());
    }
}

