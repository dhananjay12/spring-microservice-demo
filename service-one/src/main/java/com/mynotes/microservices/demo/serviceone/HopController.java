package com.mynotes.microservices.demo.serviceone;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/hop")
@Slf4j
public class HopController {

    ApplicationContext context;

    RestTemplate restTemplate;

    public HopController(RestTemplate rest,ApplicationContext context){

        this.restTemplate = rest;
        this.context = context;
    }

    @RequestMapping(value = "/{status}", method = RequestMethod.GET)
    @ResponseBody
    public String hop(@PathVariable("status") Integer status) {

        log.info("hop status passed" + status);

        String result = restTemplate.getForObject("http://service-two/status/" + status, String.class);

        return "Service one" + status + "  ++++ " + result;
    }

    @RequestMapping(value = "/message/{text}", method = RequestMethod.GET)
    @ResponseBody
    public String text(@PathVariable("text") String text) {

        log.info("Text passed==" + text);

        String result = restTemplate.getForObject("http://service-two/message/" + text, String.class);

        return "Service one+++++" + result;
    }

    @RequestMapping(value = "/headers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, String>> hopHeaders(@RequestHeader MultiValueMap<String, String> headers) {

        log.info("hop headers");

        Map<String, Map<String, String>> result = new HashMap<>();
        Map<String, String> requestHeaders = new HashMap<>();

        headers.forEach((key, value) -> {
            requestHeaders.put(key, value.stream().collect(Collectors.joining("|")));
        });
        result.put("service-one",requestHeaders);


        Map<String, String> response = restTemplate.getForEntity("http://service-two/headers", Map.class).getBody();

        result.put("service-two", response);

        return result;
    }

    @RequestMapping(value = "/headers/reactive", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, String>> hopHeadersToReactive(@RequestHeader MultiValueMap<String, String> headers) {

        log.info("hop headers to reactive");

        Map<String, Map<String, String>> result = new HashMap<>();
        Map<String, String> requestHeaders = new HashMap<>();

        headers.forEach((key, value) -> {
            requestHeaders.put(key, value.stream().collect(Collectors.joining("|")));
        });
        result.put("service-one",requestHeaders);


        Map<String, String> response = restTemplate.getForEntity("http://reactive-service/headers", Map.class).getBody();

        result.put("reactive-service", response);

        return result;
    }

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, String>> hopIp(@RequestHeader MultiValueMap<String, String> headers,
    HttpServletRequest httpServletRequest) {

        Map<String, Map<String, String>> result = new HashMap<>();

        Map<String, String> serviceOneResult = new HashMap<>();

        serviceOneResult.put("server.forward-headers-strategy",context.getEnvironment().getProperty("server.forward-headers-strategy"));
        serviceOneResult.put("x-forwarded-For",headers.getFirst("x-forwarded-for"));
        serviceOneResult.put("forwarded",headers.getFirst("forwarded"));
        serviceOneResult.put("httpServletRequest.getRequestURI()",httpServletRequest.getRequestURI());
        serviceOneResult.put("httpServletRequest.getRemoteAddr()",httpServletRequest.getRemoteAddr());

        result.put("service-one",serviceOneResult);


        Map<String, String> response = restTemplate.getForEntity("http://service-two/ip", Map.class).getBody();

        result.put("service-two", response);

        return result;
    }
}
