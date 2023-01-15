package com.mynotes.microservices.demo.gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureObservability
public class TestDownstreamCallIT extends GatewayApplicationBaseIT {

    @Test
    void test(){
        mockServer = ClientAndServer.startClientAndServer(mockServerPort);
        mockServer.when(
                        request()
                                .withPath("/test")
                )
                .respond(response()
                        .withStatusCode(200));
        webClient.get().uri("/test")
                .exchange()
                .expectStatus().isOk();
    }
}
