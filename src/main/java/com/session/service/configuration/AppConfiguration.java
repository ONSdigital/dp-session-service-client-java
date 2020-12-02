package com.session.service.configuration;

import static com.session.service.logging.SessionsClient.info;

public class AppConfiguration {

    private static AppConfiguration INSTANCE = null;

    public static final String SESSIONS_API_URL = "SESSIONS_API_URL";
    public static final String SERVICE_AUTH_TOKEN = "SERVICE_AUTH_TOKEN";

    private final String sessionsApiUrl;
    private final String serviceAuthToken;

    /**
     * @throws ConfigurationException
     */
    private AppConfiguration() throws ConfigurationException {
        this.sessionsApiUrl = sessionsApiUrl();
        this.serviceAuthToken = serviceAuthToken();

        info().data(SESSIONS_API_URL, sessionsApiUrl)
                .data(SERVICE_AUTH_TOKEN, serviceAuthToken)
                .log("successfully load application configuration");
    }

    public String sessionsApiUrl() {
        return sessionsApiUrl;
    }

    public String serviceAuthToken() {
        return serviceAuthToken;
    }

    /**
     * Return a singleton instance of the ApplicationConfiguration. Will load the ApplicationConfiguration if it has
     * not already been loaded.
     *
     * @return the application configuration.
     * @throws ConfigurationException any errors while attempting to load the configuration.
     */
    public static AppConfiguration get() throws ConfigurationException {
        if (INSTANCE == null) {
            synchronized (AppConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppConfiguration();
                }
            }
        }
        return INSTANCE;
    }
}
