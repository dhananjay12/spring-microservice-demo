package com.mynotes.microservice.productservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/products")
	public ResponseEntity<?> products() {
		List<Products> products=new ArrayList();
		products.add(new Products(1, "Product 1"));
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/bye")
	@PreAuthorize("hasAuthority('SYSADMIN')")
	public String bye() {
		return "bye";
	}

}

class Products{
	private int id;
	private String name;
	public Products(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
