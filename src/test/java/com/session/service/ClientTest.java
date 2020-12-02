package com.session.service;

import com.session.service.client.SessionsClient;
import com.session.service.client.SessionsClientImpl;
import com.session.service.entities.SessionCreated;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;

public class ClientTest {

    static final String EMAIL = "test@test.com";
    static final int SESSION_TIMEOUT_MS = 30000;

    private SessionsClient client;

    @Before
    public void setUp() throws Exception {
        client = new SessionsClientImpl("http://localhost:6666", "");
    }

    @Test
    public void createSession_shouldCreateNewSession() throws Exception {
        SessionCreated sessionCreated = client.createNewSession(EMAIL);
        assertThat(sessionCreated, is(notNullValue()));
        assertThat(sessionCreated.getId(), not(isEmptyString()));
    }

    @Test
    public void getSessionById_shouldReturnExpectedSession() throws Exception {
        SessionCreated sessionCreated = client.createNewSession(EMAIL);
        assertThat(sessionCreated, is(notNullValue()));
        assertThat(sessionCreated.getId(), not(isEmptyString()));

        Session session = client.getSessionByID(sessionCreated.getId());
        assertThat(session, is(notNullValue()));
        assertThat(session.getId(), equalTo(sessionCreated.getId()));
    }

    @Test
    public void getSessionByEmail_shouldReturnExpectedSession() throws Exception {
        SessionCreated sessionCreated = client.createNewSession(EMAIL);
        assertThat(sessionCreated, is(notNullValue()));
        assertThat(sessionCreated.getId(), not(isEmptyString()));

        Session session = client.getSessionByEmail(EMAIL);
        assertThat(session, is(notNullValue()));
        assertThat(session.getId(), equalTo(sessionCreated.getId()));
    }

    @Test
    public void getSessionByID_timeoutExpired_shouldReturnNotFound() throws Exception {
        SessionCreated sessionCreated = client.createNewSession(EMAIL);

        System.out.println("waiting a bit...");
        Thread.sleep(SESSION_TIMEOUT_MS);
        System.out.println("wait ended...");

        assertThat(client.getSessionByID(sessionCreated.getId()), is(nullValue()));
    }

    @Test
    public void clearSessions_shouldBeExpired() throws Exception {
        SessionCreated sessionCreated = client.createNewSession(EMAIL);
        assertThat(sessionCreated, is(notNullValue()));
        assertThat(sessionCreated.getId(), not(isEmptyString()));

        client.clear();

        assertThat(client.getSessionByEmail(EMAIL), is(nullValue()));
    }
}
