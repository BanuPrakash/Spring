package com.example.shopapp.client;

import com.example.shopapp.entity.Product;
import com.example.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
public class ProductClient implements CommandLineRunner {
    private final OrderService service;
    // method gets called as soon as Spring container is created and initialized
    @Override
    public void run(String... args) throws Exception {
//     insertProducts();
//     getProducts();
   //     updateProduct();
    }

    private void updateProduct() {
        service.updateProduct(2, 800.50);
    }

    private void insertProducts() {
        if(service.getProductCount() == 0) {
            service.saveProduct(Product.builder().
                    name("iPhone 15").
                    price(89000.00)
                    .quantity(100).
                    build());
            service.saveProduct(Product.builder().
                    name("Logitech moose").
                    price(450.45)
                    .quantity(100).
                    build());
            service.saveProduct(Product.builder().
                    name("Wacom").
                    price(5200.00)
                    .quantity(100).
                    build());
            service.saveProduct(Product.builder().
                    name("LG Inverter AC").
                    price(45000.00)
                    .quantity(100).
                    build());
        }
    }

    private void getProducts() {
        List<Product> products = service.getAllProducts();
        for(Product p : products) {
            System.out.println(p);
        }
    }
}
