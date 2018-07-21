package com.github.asgardbot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.getProperty;

@Component
@ConditionalOnProperty({"DIALOGFLOW_CREDENTIALS", "DIALOGFLOW_PROJECT_ID"})
class DialogflowCredentialProvider {

    private String DIALOGFLOW_CREDENTIALS;
    private Logger LOGGER = LoggerFactory.getLogger(DialogflowCredentialProvider.class);
    private static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    private static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public DialogflowCredentialProvider(Environment environment) throws IOException {
        DIALOGFLOW_CREDENTIALS = environment.getProperty("DIALOGFLOW_CREDENTIALS");

        File configPath = new File(getProperty("user.home", ""), ".config");
        File cloudConfigPath = new File(configPath, CLOUDSDK_CONFIG_DIRECTORY);
        File credentialFilePath = new File(cloudConfigPath, WELL_KNOWN_CREDENTIALS_FILE);
        FileWriter writer = null;

        try {
            cloudConfigPath.mkdirs();
            credentialFilePath.createNewFile();
            writer = new FileWriter(credentialFilePath);
            LOGGER.info("Attempting to write Dialogflow credentials");
            writer.write(DIALOGFLOW_CREDENTIALS);
            LOGGER.info("Successfully written Dialogflow credentials");
        } catch (IOException e) {
            LOGGER.error("Failed to set up Dialogflow credential file: '{}' {}", credentialFilePath, e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}


