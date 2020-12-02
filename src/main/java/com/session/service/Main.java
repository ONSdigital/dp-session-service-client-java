package com.session.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.session.service.client.SessionsClient;
import com.session.service.client.SessionsClientImpl;
import com.session.service.entities.SessionCreated;
import com.github.onsdigital.logging.v2.DPLogger;
import com.github.onsdigital.logging.v2.Logger;
import com.github.onsdigital.logging.v2.LoggerImpl;
import com.github.onsdigital.logging.v2.LoggingException;
import com.github.onsdigital.logging.v2.config.Builder;
import com.github.onsdigital.logging.v2.serializer.JacksonLogSerialiser;
import com.github.onsdigital.logging.v2.serializer.LogSerialiser;
import com.github.onsdigital.logging.v2.storage.LogStore;
import com.github.onsdigital.logging.v2.storage.MDCLogStore;

import static com.session.service.logging.SessionsClient.info;
import static com.session.service.logging.SessionsClient.fatal;

public class Main {

    public static void main(String[] args) {

        try {
            startApp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO -- not sure what the main method should look like.
        SessionsClient sessionClient = new SessionsClientImpl("http://localhost:6666", "");
        Gson g = new GsonBuilder().setPrettyPrinting().create();

        try {
            System.out.println("create session");
            SessionCreated sessionCreated = sessionClient.createNewSession("test@test.com");
            System.out.println(g.toJson(sessionCreated));

            System.out.println("Get session by ID");
            Session session = sessionClient.getSessionByID(sessionCreated.getId());
            System.out.println(g.toJson(session));


            System.out.println("waiting");
            Thread.sleep(11000);

            System.out.println("Get session by email");
            Session session1 = sessionClient.getSessionByEmail("test@test.com");
            if (session1 == null) {
                System.out.println("session not found");
            } else {
                System.out.println(g.toJson(session1));
            }

            System.out.println("waiting again");
            Thread.sleep(31000);
            session = sessionClient.getSessionByID(session1.getId());
            if (session == null) {
                System.out.println("session not found");
            } else {
                System.out.println(g.toJson(session));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void startApp() throws Exception {
        initLogging();

        info().log("starting sessions-service-client");
    }

    private static void initLogging() throws LoggingException {
        LogSerialiser serialiser = new JacksonLogSerialiser();
        LogStore store = new MDCLogStore(serialiser);
        Logger logger = new LoggerImpl("sessions-service-client");

        DPLogger.init(new Builder()
                .logger(logger)
                .logStore(store)
                .serialiser(serialiser)
                .dataNamespace("sessions-service-client.data")
                .create());
    }

}
