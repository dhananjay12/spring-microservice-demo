package com.mynotes.microservices.demo.servicetwo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ServiceTwoController {

    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
    public ResponseEntity<?> status(@PathVariable("status") Integer status) {
        return ResponseEntity.status(status).body("response from service two");
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello from service two";
    }

    @RequestMapping(value = "/message/{text}", method = RequestMethod.GET)
    public ResponseEntity<?> text(@PathVariable("text") String text) {
        log.info(text);
        log.warn(text);
        log.debug(text);
        text = text + "+++++Service two";
        return ResponseEntity.status(HttpStatus.OK).body(text);

    }

    @RequestMapping(value = "/calculate/divide/{a}/{b}", method = RequestMethod.GET)
    @ResponseBody
    public Integer divide(@PathVariable("a") Integer a, @PathVariable("b") Integer b) {

        log.debug("Passed values {}/{}", a, b);

        int result = a / b;

        return result;
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

}
