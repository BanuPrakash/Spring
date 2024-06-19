package com.example.shopapp.client;

import com.example.shopapp.entity.Customer;
import com.example.shopapp.entity.LineItem;
import com.example.shopapp.entity.Order;
import com.example.shopapp.entity.Product;
import com.example.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor

public class OrderClient implements CommandLineRunner {
    private final OrderService service;
    @Override
    public void run(String... args) throws Exception {
     //   placeOrder();
        getOrder();
    }

    private void getOrder() {
        Order order = service.getOrder(1);
        System.out.println(order.getOrderDate() +" ," + order.getTotal() + ", " + order.getCustomer().getFirstName());
        for(LineItem item : order.getItems()) {
            System.out.println(item);
        }
    }
    private void placeOrder() {
        Order order = new Order();

        Customer c = new Customer();
        c.setEmail("roger@gmail.com");
        order.setCustomer(c);

        List<LineItem> items = new ArrayList<>();
        items.add(LineItem.builder()
                .product(Product.builder().id(4).build()).qty(3).build());
        items.add(LineItem.builder()
                .product(Product.builder().id(1).build()).qty(2).build());
        order.setItems(items);

        service.placeOrder(order);
    }
}
