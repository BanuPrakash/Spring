package com.example.shopapp.client;

import com.example.shopapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class ProductRestClient implements CommandLineRunner {
    private final RestTemplate template;
    @Override
    public void run(String... args) throws Exception {
//        getUsers();
        getProduct();
        getAllProducts();
    }

    private void getAllProducts() {
        ResponseEntity<List<Product>> response = template.exchange("http://localhost:8080/api/products",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});

        List<Product> products = response.getBody();
        for(Product p : products) {
            System.out.println(p);
        }
    }
    private void getProduct() {
        ResponseEntity<Product> response = template.getForEntity("http://localhost:8080/api/products/1", Product.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody()); // Product
    }
    private void getUsers() {
        String result = template.getForObject("https://jsonplaceholder.typicode.com/users", String.class);
        System.out.println(result);
    }
}
