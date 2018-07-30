package com.mynotes.microservice.productservice;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
	@GetMapping("/hello")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String sayHello() {
		return "Hello";
	}
	
	@GetMapping("/hi")
	public String sayHi() {
		return "Hi";
	}
	
	@GetMapping("/bye")
	@PreAuthorize("hasAuthority('SYSADMIN')")
	public String bye() {
		return "bye";
	}

}
