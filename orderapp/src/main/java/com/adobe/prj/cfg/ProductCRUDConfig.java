package com.adobe.prj.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

@Configuration
public class ProductCRUDConfig implements CommandLineRunner {
	
	@Autowired
	private OrderService service;
	
	@Override
	public void run(String... args) throws Exception {
//		addProducts();
//		listProducts();
	}

	private void listProducts() {
		List<Product> products = service.getProducts();
		for(Product p : products) {
			System.out.println(p); // toString()
		}
	}

	private void addProducts() {
		Product p1 = Product.builder()
					.name("Logitech Mouse")
					.price(980.00)
					.quantity(100)
					.build();
	
		Product p2 = Product.builder()
				.name("LG AC")
				.price(45000.00)
				.quantity(100)
				.build();
		
		service.insertProduct(p1);
		service.insertProduct(p2);
	}

}
