package com.mynotes.microservices.demo.gateway;

import brave.CurrentSpanCustomizer;
import brave.Span;
import brave.SpanCustomizer;
import brave.Tracer;
import brave.Tracing;
import brave.test.TestSpanHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.sleuth.brave.BraveTestTracing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("external")
public class ExternalServiceGatewayFilterFactoryIT extends GatewayApplicationBaseIT {


    TestSpanHandler spans = new TestSpanHandler();
    Tracing tracing = Tracing.newBuilder().addSpanHandler(spans).build();
    Span span = tracing.tracer().newTrace();
    SpanCustomizer spanCustomizer = span.customizer();


    @Test
    void test() {

        //mock external server to reponse with 200 and dummy value
        mockExternalServer.when(
                        request()
                                .withPath("/external-service")
                )
                .respond(response()
                        .withStatusCode(200)
                        .withBody("dummy"));

        // Mock downstream test service to reponse with 200 if request header has "External-Service-Header" header key with value "dummy"
        mockTestServer.when(
                        request()
                                .withPath("/test")
                                .withHeader("External-Service-Header", "dummy")
                )
                .respond(response()
                        .withStatusCode(200));

        webClient.get().uri("/test")
                .exchange()
                .expectStatus().isOk();

        //assert spans count
//        assertEquals(2, spans.spans().size());
//        span.finish();
//        BraveTestTracing testTracing;

    }
}
