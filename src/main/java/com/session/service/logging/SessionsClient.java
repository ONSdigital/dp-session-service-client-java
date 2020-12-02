package com.session.service.logging;

import com.github.onsdigital.logging.v2.event.BaseEvent;
import com.github.onsdigital.logging.v2.event.Severity;

import static com.github.onsdigital.logging.v2.DPLogger.logConfig;

public class SessionsClient extends BaseEvent<SessionsClient> {

    public static SessionsClient info() {
        return new SessionsClient(logConfig().getNamespace(), Severity.INFO);
    }

    public static SessionsClient error() {
        return new SessionsClient(logConfig().getNamespace(), Severity.ERROR);
    }

    public static SessionsClient fatal() {
        return new SessionsClient(logConfig().getNamespace(), Severity.FATAL);
    }

    public SessionsClient(String namespace, Severity severity) {
        super(namespace, severity, logConfig().getLogStore());
    }

}
