package com.xiaomi.prj.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xiaomi.prj.entity.Product;
import com.xiaomi.prj.service.OrderService;

@Component
public class ProductClient implements CommandLineRunner {
	
	@Autowired
	private OrderService service;
	
	// this method executes as soon as Spring container is initialized
	@Override
	public void run(String... args) throws Exception {
//		insertProducts();
//		listProducts();
//		singleProduct();
//		fetchByRange();
//		updateProduct();
	}

	private void updateProduct() {
		service.updateProduct(4, 62000.00);
	}

	private void fetchByRange() {
		List<Product> products = service.getByRange(500, 1_00_000);
		for(Product p : products) {
			System.out.println(p);
		}
	}

	private void singleProduct() {
		Product p = service.getProductById(1);
		System.out.println(p);
	}

	private void listProducts() {
		List<Product> products = service.getProducts();
		for(Product p : products) {
			System.out.println(p);
		}
	}

	private void insertProducts() {
		service.addProduct(Product.builder()
					.name("iPhone 14")
					.price(120000.00)
					.quantity(100)
					.build());
		
		service.addProduct(Product.builder()
				.name("Sony Bravia")
				.price(230000.00)
				.quantity(100)
				.build());
		
		service.addProduct(Product.builder()
				.name("HDMI Connector")
				.price(500.00)
				.quantity(100)
				.build());
		
		service.addProduct(Product.builder()
				.name("Redme X-12")
				.price(60000.00)
				.quantity(100)
				.build());
	}

}
