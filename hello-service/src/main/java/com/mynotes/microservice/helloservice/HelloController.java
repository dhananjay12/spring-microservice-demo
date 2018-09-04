package com.mynotes.microservice.helloservice;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/hello")	
	public String sayHello( @RequestHeader Map<String,String> headers ) {

		for (String elem: headers.keySet()) {

      System.out.println(elem + " : " + headers.get(elem));

    }

		return "Hello";
	}	

}
