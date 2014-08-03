/*
 * Copyright (C) 2007-2014, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.interviewtask.httpproxy;

import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;

import net.jadler.JadlerMocker;
import net.jadler.stubbing.server.jetty.JettyStubHttpServer;

class TestSupport {

    static final int BACKEND1_PORT = 8081;
    static final int BACKEND2_PORT = 8082;

    private static JadlerMocker backend1 = new JadlerMocker(new JettyStubHttpServer(BACKEND1_PORT));
    private static JadlerMocker backend2 = new JadlerMocker(new JettyStubHttpServer(BACKEND2_PORT));

    static JadlerMocker backend1() {
        return backend1;
    }

    static JadlerMocker backend2() {
        return backend2;
    }

    static void mockPing(JadlerMocker backend) {
        mockPing(backend, 0);
    }

    static void mockPing(JadlerMocker backend, long responseDelay) {
        mockPing(backend, responseDelay, HttpStatus.OK.value());
    }

    static void mockPing(JadlerMocker backend, long responseDelay, int httpStatus) {
        backend
        .onRequest()
            .havingPathEqualTo("/ping")
            .havingMethodEqualTo("GET")
        .respond()
            .withStatus(httpStatus)
            .withDelay(responseDelay, TimeUnit.SECONDS)
            .withBody("pong");
    }

    static void mockAlive(JadlerMocker backend) {
        mockAlive(backend, 0);
    }

    static void mockAlive(JadlerMocker backend, long responseDelay) {
        int port = backend.getStubHttpServerPort();
        backend.onRequest()
            .havingPathEqualTo("/alive")
            .havingMethodEqualTo("GET")
        .respond()
            .withStatus(200)
            .withDelay(responseDelay, TimeUnit.SECONDS)
            .withBody("{\"backend\":{\"id\":" + port + "}}");
    }
}
