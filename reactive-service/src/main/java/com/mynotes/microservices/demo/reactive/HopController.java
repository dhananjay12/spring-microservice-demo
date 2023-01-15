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

@RestController
@RequestMapping("/hop")
@Slf4j
public class HopController {

    private final WebClient webClient;

    public HopController(WebClient webClient) {
        this.webClient = webClient;
    }

    @RequestMapping(value = "/headers/service-one", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, String>> hopHeadersToReactive(@RequestHeader MultiValueMap<String, String> headers) {

        log.info("hop headers to reactive");

        Map<String, Map<String, String>> result = new HashMap<>();
        Map<String, String> requestHeaders = new HashMap<>();

        headers.forEach((key, value) -> {
            requestHeaders.put(key, value.stream().collect(Collectors.joining("|")));
        });
        result.put("reactive-service",requestHeaders);


        Map<String, String> response = this.webClient.get().uri("http://service-one/headers").retrieve().bodyToMono(Map.class).block(); //Not being reactive

        result.put("service-one", response);

        return result;
    }
}
