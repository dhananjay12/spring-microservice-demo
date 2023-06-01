package com.mynotes.microservices.demo.gateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
public class ExternalServiceGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private final WebClient webClient;

    @Value("${app.external.service.url}")
    public String externalServiceUrl;

//    public ExternalServiceGatewayFilterFactory(WebClient.Builder client) {
//        this.webClient = client.build();
//    }

    public ExternalServiceGatewayFilterFactory(HttpClient httpClient) {
        this.webClient = WebClient.builder().clientConnector(
                new ReactorClientHttpConnector(httpClient)).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> webClient
                .get()
                .uri(externalServiceUrl)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(RuntimeException::new))
                .toEntity(String.class)
                .flatMap(response -> {
                    //set request header
                    ServerHttpRequest request = exchange.getRequest().mutate()
                            .headers(httpHeaders -> httpHeaders.add("External-Service-Header", response.getBody())).build();
                    return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(() -> {
                        exchange.getResponse().getHeaders().add("test", "test");
                    }));
                });
    }


}
