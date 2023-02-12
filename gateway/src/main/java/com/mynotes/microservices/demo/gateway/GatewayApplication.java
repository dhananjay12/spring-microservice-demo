package com.mynotes.microservices.demo.gateway;

import brave.Tracer;
import brave.baggage.BaggageField;
import brave.propagation.TraceContext;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;


@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class GatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}

@Component
class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(CustomGlobalFilter.class);
    private final Tracer tracer;

    CustomGlobalFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Custom global filter");
        BaggageField.getByName(context(), "test").updateValue("123");
        tracer.currentSpanCustomizer().tag("test", "123");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private TraceContext context() {
        return Optional.ofNullable(tracer.currentSpan()).orElseGet(tracer::newTrace).context();
    }
}
