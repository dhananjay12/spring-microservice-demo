package com.mynotes.microservices.demo.serviceone;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ServiceOneController {

    @Autowired
    ApplicationContext context;

    RestTemplate restTemplate;

    public ServiceOneController(RestTemplate rest){
        this.restTemplate = rest;
    }

    @RequestMapping(value = "/hop/{status}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {

        return "Hello from service one";
    }

    @GetMapping("/headers")
    @ResponseBody
    public Map<String, String> headers(@RequestHeader MultiValueMap<String, String> headers) {

        Map<String, String> map = new HashMap<>();

        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
            map.put(key, value.stream().collect(Collectors.joining("|")));
        });

        return map;
    }

    @RequestMapping(value = "/hop/headers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, String>> hopHeaders(@RequestHeader MultiValueMap<String, String> headers) {

        log.info("hop headers");
        log.info("server.forward-headers-strategy = "+ context.getEnvironment().getProperty("server.forward-headers-strategy"));

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

}
