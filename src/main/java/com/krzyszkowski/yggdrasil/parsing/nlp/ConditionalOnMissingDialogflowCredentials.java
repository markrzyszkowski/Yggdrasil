package com.krzyszkowski.yggdrasil.parsing.nlp;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;

class ConditionalOnMissingDialogflowCredentials extends NoneNestedConditions {

    public ConditionalOnMissingDialogflowCredentials() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty({"DIALOGFLOW_CREDENTIALS", "DIALOGFLOW_PROJECT_ID"})
    private static class MissingDialogflowCredentials {

    }
}
