package com.adobe.prj.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.adobe.prj.entity.Product;

//@Component
public class RestTemplateExample implements CommandLineRunner {
	@Autowired
	RestTemplate restTemplate;
	
	public void getProducts() {
		String json = restTemplate.getForObject("http://localhost:8080/api/products", String.class);
		System.out.println(json);
	}
	
	public void getProductsById() {
		ResponseEntity<Product> entity = restTemplate.getForEntity("http://localhost:8080/api/products/2", Product.class);
		System.out.println(entity.getBody());
	}
	
	public void addProduct() {
		Product p = 
				Product.builder().name("A")
				.price(100.00).quantity(100).build();
		
		ResponseEntity<Product> entity =
				restTemplate.postForEntity("http://localhost:8080/api/products", p, Product.class);
		
		System.out.println(entity.getStatusCodeValue()); // 201
	}

	@Override
	public void run(String... args) throws Exception {
		addProduct();
		getProducts();
		getProductsById();

	}
		
}
