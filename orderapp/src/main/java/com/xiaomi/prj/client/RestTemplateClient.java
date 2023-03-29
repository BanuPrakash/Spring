package com.xiaomi.prj.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.xiaomi.prj.entity.Product;

@Component
public class RestTemplateClient implements CommandLineRunner {
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public void run(String... args) throws Exception {
		//getUsers();
//		getProduct();
	//	addProduct();
		getAllProducts();
	}

	private void getAllProducts() {
		
		ResponseEntity<List<Product>> exchange = 		    		
		   restTemplate.exchange("http://localhost:8080/api/products", 
		    				HttpMethod.GET, null,new ParameterizedTypeReference<List<Product>>(){});
		    
   			List<Product> entities = exchange.getBody();
		    System.out.println(entities);	
	}

	private void addProduct() {
		Product p = Product.builder().name("Tata Play").price(9800.00).quantity(500).build();
		restTemplate.postForEntity("http://localhost:8080/api/products", p, Product.class);
		System.out.println("Product added!!!");
	}

	private void getProduct() {
		ResponseEntity<Product> response = 
				restTemplate.getForEntity("http://localhost:8080/api/products/3", Product.class);
		
		System.out.println(response.getStatusCodeValue());
		Product p = response.getBody();
		System.out.println(p);
	}

	private void getUsers() {
		String result = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", String.class);
		System.out.println(result);
	}

}
