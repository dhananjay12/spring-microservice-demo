package com.mynotes.microservices.demo.reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hop")
@Slf4j
public class HopController {

    private final WebClient webClient;

    public HopController(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @RequestMapping(value = "/headers/service-one", method = RequestMethod.GET)
    @ResponseBody
    public Mono<Map<String, Map<String, String>>> hopHeadersToReactive(@RequestHeader MultiValueMap<String, String> headers) {

        log.info("hop headers to reactive");

        Map<String, Map<String, String>> result = new HashMap<>();
        Map<String, String> requestHeaders = new HashMap<>();

        headers.forEach((key, value) -> {
            requestHeaders.put(key, value.stream().collect(Collectors.joining("|")));
        });
        result.put("reactive-service",requestHeaders);


        return this.webClient.get().uri("http://service-one/headers").retrieve().bodyToMono(Map.class)
                .doOnSuccess(response -> result.put("service-one", response))
                .thenReturn(result);
    }
}
