package com.mynotes.microservices.demo.gateway;

import ch.qos.logback.classic.Logger;
import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.socket.PortFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

public class GatewayApplicationBaseIT {

    protected static int mockServerPort;
    protected static ClientAndServer mockServer;
    protected WebTestClient webClient;
    @LocalServerPort
    private int port;

    @BeforeAll
    public static void beforeClass() {

        LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.TRACE);

        mockServerPort = PortFactory.findFreePort();

        System.setProperty("it.mock.server.port", String.valueOf(mockServerPort));

    }

    @AfterAll
    public static void afterClass() {
        System.clearProperty("it.mock.server.port");
    }

    @BeforeEach
    public void setUp() {
        webClient = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(5))
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @AfterEach
    public void afterEach() {
        mockServer.stop();
        webClient.delete();
    }

}
