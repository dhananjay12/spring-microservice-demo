package com.mynotes.microservices.demo.serviceone;

import jakarta.servlet.http.HttpServletRequest;
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

    ApplicationContext context;

    RestTemplate restTemplate;

    public ServiceOneController(RestTemplate rest,ApplicationContext context){
        this.restTemplate = rest;
        this.context = context;
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

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> ip(@RequestHeader MultiValueMap<String, String> headers,
                                                  HttpServletRequest httpServletRequest) {

        Map<String, String> serviceOneResult = new HashMap<>();

        serviceOneResult.put("server.forward-headers-strategy",context.getEnvironment().getProperty("server.forward-headers-strategy"));
        serviceOneResult.put("x-forwarded-For",headers.getFirst("x-forwarded-for"));
        serviceOneResult.put("forwarded",headers.getFirst("forwarded"));
        serviceOneResult.put("httpServletRequest.getRequestURI()",httpServletRequest.getRequestURI());
        serviceOneResult.put("httpServletRequest.getRemoteAddr()",httpServletRequest.getRemoteAddr());

        return serviceOneResult;
    }

}
