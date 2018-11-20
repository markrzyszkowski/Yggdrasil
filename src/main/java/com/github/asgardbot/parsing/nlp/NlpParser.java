package com.github.asgardbot.parsing.nlp;

import com.github.asgardbot.commons.Request;
import com.github.asgardbot.parsing.Parser;
import com.github.asgardbot.parsing.QueryDto;
import com.google.cloud.dialogflow.v2beta1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@Qualifier("nlp")
@ConditionalOnProperty({"DIALOGFLOW_CREDENTIALS", "DIALOGFLOW_PROJECT_ID"})
public class NlpParser implements Parser {

    private Parser exclamationParser;
    private static final String LANG = "en-US";
    private final Logger LOGGER = LoggerFactory.getLogger(NlpParser.class);

    @Value("#{systemEnvironment['DIALOGFLOW_PROJECT_ID']}")
    private String PROJECT_ID;

    public NlpParser(@Qualifier("mainParser") @Lazy Parser exclamationParser) {
        this.exclamationParser = exclamationParser;
    }

    @Override
    public Request parse(QueryDto query) {
        try {
            if (query.isFromDialogflow()) {
                throw new IllegalArgumentException("A request from NlpParser has returned to the NlpParser!");
            }

            QueryResult dialogflowQueryResult = queryDialogflow(query);

            String exclamationQuery = formQuery(dialogflowQueryResult);
            final QueryDto exclamationQueryDto =
              new QueryDto().withQueryText(exclamationQuery)
                            .withSessionId(query.getSessionId())
                            .markAsDialogflow();
            return exclamationParser.parse(exclamationQueryDto);
        } catch (Exception e) {
            LOGGER.error("Error occured while invoking Dialogflow: '{}'", e);
            return null;
        }
    }

    private QueryResult queryDialogflow(QueryDto query) throws Exception {
        String queryText = query.getQueryText();
        String sessionId = query.getSessionId();
        SessionName sessionName = SessionName.of(PROJECT_ID, sessionId);

        LOGGER.info("Building a Dialogflow request");
        LOGGER.debug("for user message: '{}'", query);

        SessionsClient sessionsClient = SessionsClient.create();
        TextInput.Builder textInput = TextInput.newBuilder().setText(queryText).setLanguageCode(LANG);
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        LOGGER.info("Querying Dialogflow");

        DetectIntentResponse response = sessionsClient.detectIntent(sessionName, queryInput);
        return response.getQueryResult();
    }

    private String formQuery(QueryResult queryResult) {
        String action = queryResult.getAction();
        final Map<String, com.google.protobuf.Value> parameters = queryResult.getParameters().getFieldsMap();
        final String fulfillmentText = queryResult.getFulfillmentText();

        logDialogflowResults(queryResult, action, parameters, fulfillmentText);

        if (isNullOrEmpty(fulfillmentText)) {
            return QueryFactory.formQuery(action, parameters);
        } else {
            LOGGER.info("Responding with fulfillment");
            return "!id " + fulfillmentText;
        }
    }

    @Override
    public String getCommand() {
        return "Try also queries in natural language";
    }

    private void logDialogflowResults(QueryResult queryResult,
                                      String action,
                                      Map<String, com.google.protobuf.Value> parameters,
                                      String fulfillmentText) {
        LOGGER.info("Dialogflow action: '{}'", action);
        LOGGER.info("Intent is '{}' with '{}' confidence",
                    queryResult.getIntent().getDisplayName(),
                    queryResult.getIntentDetectionConfidence());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Detected parameters: '{}'", parameters.toString());
            LOGGER.debug("Fulfillment text: '{}'", fulfillmentText);
            LOGGER.debug("Payload: '{}'", queryResult);
        }
    }
}
