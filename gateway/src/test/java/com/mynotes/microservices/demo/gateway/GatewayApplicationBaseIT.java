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

    protected static int mockTestServerPort;
    protected static int mockExternalServerPort;
    protected static ClientAndServer mockTestServer;
    protected static ClientAndServer mockExternalServer;
    protected WebTestClient webClient;
    @LocalServerPort
    private int port;

    @BeforeAll
    public static void beforeClass() {

        LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.TRACE);

        mockTestServerPort = PortFactory.findFreePort();
        mockExternalServerPort = PortFactory.findFreePort();

        System.setProperty("it.mock.server.port", String.valueOf(mockTestServerPort));
        System.setProperty("it.external.server.port", String.valueOf(mockExternalServerPort));
    }

    @AfterAll
    public static void afterClass() {
        System.clearProperty("it.mock.server.port");
        System.clearProperty("it.external.server.port");
        System.clearProperty("it.mock.zipkin.port");
    }

    @BeforeEach
    public void setUp() {
        webClient = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(60))
                .baseUrl("http://localhost:" + port)
                .build();
        mockTestServer = ClientAndServer.
                startClientAndServer(mockTestServerPort);
        mockExternalServer = ClientAndServer.
                startClientAndServer(mockExternalServerPort);
    }

    @AfterEach
    public void afterEach() {
        mockTestServer.stop();
        mockExternalServer.stop();
        webClient.delete();
    }

}