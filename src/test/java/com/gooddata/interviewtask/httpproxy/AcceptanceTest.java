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

/**
 * This test defines acceptance criteria for the HTTP proxy. The backends are mocked
 * including their expected behavior.
 *
 */
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

    /**
     * Fires a GET HTTP request <code>http://localhost:8080/backends</code> on the proxy and
     * expects the following HTTP response.
     *
     * <pre>
     *  <code>
     *  {
     *  "backends":[
     *    {
     *      "backend":{
     *        "id":"%ID"
     *      }
     *    },
     *    {
     *      "backend":{
     *        "id":"%ID"
     *      }
     *    }
     *  ]
     * }
     *  </code>
     * </pre>
     *
     * The proxy is supposed to issue a GET request on the path <code>/alive</code>
     * to both backends, merge the responses and returns it.
     */
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

    /**
     * Fires a GET HTTP request <code>http://localhost:8080/ping</code> with the
     * HTTP header <code>X-Backend-id: 8081</code> on the proxy and expects the request
     * is delivered to the backend identified by id <code>8081</code>.
     */
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

   /**
    * Fires a GET HTTP request <code>http://localhost:8080/ping</code>, both
    * backends do not respond at the specified timeout which is 5 seconds
    * (the mocked server delays the response for 60 seconds). The test expects
    * the proxy will respond with HTTP status 503.
    */
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

    /**
     * Fires a GET HTTP request <code>http://localhost:8080/ping</code> the backend 1
     * is temporary unavailable so the proxy will dispatches the request to the backend 2.
     */
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
