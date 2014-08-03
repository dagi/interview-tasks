/*
 * Copyright (C) 2007-2014, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.interviewtask.httpproxy;

import static com.gooddata.interviewtask.httpproxy.TestSupport.backend1;
import static com.gooddata.interviewtask.httpproxy.TestSupport.backend2;
import static com.gooddata.interviewtask.httpproxy.TestSupport.mockAlive;
import static com.gooddata.interviewtask.httpproxy.TestSupport.mockPing;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.restfire.RestFire.fire;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import net.jadler.JadlerMocker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AcceptanceTest {

    private JadlerMocker backend1 = backend1();
    private JadlerMocker backend2 = backend2();

    @Before
    public void startBackends() {
        backend1.start();
        backend2.start();
    }

    @After
    public void stopBackends() {
        backend1.close();
        backend2.close();
    }

    @Test
    public void listBackends() {
        mockAlive(backend1);
        mockAlive(backend2);
        fire()
            .get()
                .to("http://localhost:8080/backends")
                .withHeader("Accept", "application/json")
            .expectResponse()
                .havingStatusEqualTo(OK.value())
                .havingBody(jsonEquals("{\"backends\":[{\"backend\":{\"id\":8081}},{\"backend\":{\"id\":8082}}]}"));


        backend1.verifyThatRequest().havingPathEqualTo("/alive").receivedOnce();
        backend2.verifyThatRequest().havingPathEqualTo("/alive").receivedOnce();
    }

    @Test
    public void dispatchToPrefferedBackend() {
        mockAlive(backend1);
        mockAlive(backend2);
        mockPing(backend1);
        mockPing(backend2);

        fire()
            .get()
                .to("http://localhost:8080/ping")
                .withHeader("Accept", "text/plain")
                .withHeader("X-Backend-id", "8081")
            .expectResponse()
                .havingStatusEqualTo(OK.value())
                .havingBodyEqualTo("pong");

        backend1.verifyThatRequest().havingPathEqualTo("/ping").receivedOnce();
        backend2.verifyThatRequest().havingPathEqualTo("/ping").receivedNever();
    }

    @Test
    public void delayedResponse() {
        mockAlive(backend1);
        mockAlive(backend2);
        mockPing(backend1, 60, SERVICE_UNAVAILABLE.value());
        mockPing(backend2, 60, SERVICE_UNAVAILABLE.value());

        fire()
            .get()
                .to("http://localhost:8080/ping")
                .withHeader("Accept", "text/plain")
            .expectResponse()
                .havingStatusEqualTo(SERVICE_UNAVAILABLE.value());
    }

    @Test
    public void fallbackToHealthyBackend() {
        mockAlive(backend1);
        mockAlive(backend2);
        mockPing(backend1, 0, SERVICE_UNAVAILABLE.value());
        mockPing(backend2);

        fire()
            .get()
                .to("http://localhost:8080/ping")
                .withHeader("Accept", "text/plain")
            .expectResponse()
                .havingStatusEqualTo(OK.value())
                .havingBodyEqualTo("pong");
    }
}
