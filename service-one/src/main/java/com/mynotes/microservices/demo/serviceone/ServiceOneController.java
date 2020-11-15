package com.mynotes.microservices.demo.serviceone;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    RestTemplate rest;

    @RequestMapping(value = "/hop/{status}", method = RequestMethod.GET)
    @ResponseBody
    public String hop(@PathVariable("status") Integer status) {

        log.info("hop status passed" + status);

        String result = rest.getForObject("http://service-two/status/" + status, String.class);

        return "Service one" + status + "  ++++ " + result;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {

        return "Hello from service one";
    }

    @GetMapping("/headers")
    public ResponseEntity<?> headers(@RequestHeader MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });

        return ResponseEntity.ok(headers);
    }

}
