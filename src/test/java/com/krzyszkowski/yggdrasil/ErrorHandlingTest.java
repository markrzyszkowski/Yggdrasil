package com.krzyszkowski.yggdrasil;

import com.krzyszkowski.yggdrasil.chat.RequestReceiver;
import com.krzyszkowski.yggdrasil.chat.ResponseDispatcher;
import com.krzyszkowski.yggdrasil.commons.ExternalApiException;
import com.krzyszkowski.yggdrasil.commons.InvalidRequestException;
import com.krzyszkowski.yggdrasil.commons.InvalidResponseException;
import com.krzyszkowski.yggdrasil.commons.Request;
import com.krzyszkowski.yggdrasil.core.MessageDispatcher;
import com.krzyszkowski.yggdrasil.dataproviders.DataProvider;
import com.krzyszkowski.yggdrasil.parsing.Parser;
import com.krzyszkowski.yggdrasil.rqrs.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class ErrorHandlingTest {

    @MockBean
    @Qualifier("router")
    private ResponseDispatcher dispatcher;
    @MockBean
    @Qualifier("mainParser")
    private Parser parser;
    @MockBean
    @Qualifier("orchestrator")
    private DataProvider orchestrator;
    @Autowired
    private RequestReceiver receiver;
    @Autowired
    private MessageDispatcher router;

    @Before
    public void setup() throws Exception {
        doThrow(new RuntimeException()).when(parser).parse(any());
        doThrow(new InvalidRequestException()).when(orchestrator).process(any(NonExistentRequest.class));
        doThrow(new InvalidResponseException()).when(orchestrator).process(any(BadRequest.class));
        doThrow(new ExternalApiException(HttpStatus.BAD_REQUEST, "Bad"))
                .when(orchestrator).process(any(BadHttpRequest.class));
        doThrow(new Exception()).when(orchestrator).process(any(GeneralErrorRequest.class));
        doNothing().when(dispatcher).awaitResponse(anyString());
    }

    @Test
    public void errorHandling_parsingError_shouldReturnSomethingWentWrong() {
        //given
        String sessionId = "DUMMY_SESSION";
        String expectedMessage = "Something went wrong";
        //when
        receiver.handleMessage(sessionId, "Unparseable message", dispatcher);
        //then
        verify(dispatcher, times(1)).awaitResponse(anyString());
        verify(dispatcher, times(1)).enqueueResponse(any());
        verify(dispatcher).enqueueResponse(isA(ErrorResponse.class));
        verify(dispatcher).enqueueResponse(argThat(e -> e.getTransactionId().equals(sessionId)));
        verify(dispatcher).enqueueResponse(argThat(e -> ((ErrorResponse)e).getMessage().equals(expectedMessage)));
    }

    @Test
    public void errorHandling_serviceNotFound_shouldReturnBadRequest() throws InterruptedException {
        //given
        String expectedMessage = "Could not process request ";
        //when
        router.enqueueRequest(new NonExistentRequest());
        Thread.sleep(250);
        //then
        verify(dispatcher, times(1)).enqueueResponse(any());
        verify(dispatcher).enqueueResponse(isA(ErrorResponse.class));
        verify(dispatcher).enqueueResponse(argThat(e -> ((ErrorResponse)e).getMessage().equals(expectedMessage)));
    }

    @Test
    public void errorHandling_serviceError_shouldReturnBadResponse() throws InterruptedException {
        //given
        String expectedMessage = "Could not process response";
        //when
        router.enqueueRequest(new BadRequest());
        Thread.sleep(250);
        //then
        verify(dispatcher, times(1)).enqueueResponse(any());
        verify(dispatcher).enqueueResponse(isA(ErrorResponse.class));
        verify(dispatcher).enqueueResponse(argThat(e -> ((ErrorResponse)e).getMessage().equals(expectedMessage)));
    }

    @Test
    public void errorHandling_apiServerError_shouldReturnErrorCode() throws InterruptedException {
        //given
        String expectedMessage = "Server responded with an error code 400 BAD_REQUEST";
        //when
        router.enqueueRequest(new BadHttpRequest());
        Thread.sleep(250);
        //then
        verify(dispatcher, times(1)).enqueueResponse(any());
        verify(dispatcher).enqueueResponse(isA(ErrorResponse.class));
        verify(dispatcher).enqueueResponse(argThat(e -> ((ErrorResponse)e).getMessage().equals(expectedMessage)));
    }

    @Test
    public void errorHandling_generalError_shouldReturnGenericMessage() throws InterruptedException {
        //given
        String expectedMessage = "General error";
        //when
        router.enqueueRequest(new GeneralErrorRequest());
        Thread.sleep(250);
        //then
        verify(dispatcher, times(1)).enqueueResponse(any());
        verify(dispatcher).enqueueResponse(isA(ErrorResponse.class));
        verify(dispatcher).enqueueResponse(argThat(e -> ((ErrorResponse)e).getMessage().equals(expectedMessage)));
    }

    private static class NonExistentRequest extends Request<NonExistentRequest> {

    }

    private static class BadRequest extends Request<BadRequest> {

    }

    private static class BadHttpRequest extends Request<BadHttpRequest> {

    }

    private static class GeneralErrorRequest extends Request<GeneralErrorRequest> {

    }
}
