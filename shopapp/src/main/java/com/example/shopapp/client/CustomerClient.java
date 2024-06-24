package com.example.shopapp.client;

import com.example.shopapp.entity.Customer;
import com.example.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class CustomerClient implements CommandLineRunner {
    private  final OrderService service;
    @Override
    public void run(String... args) throws Exception {
//        addCustomers();
    }

    private void addCustomers() {
        if(service.getCustomerCount() == 0) {
            service.saveCustomer(Customer.builder().
                    email("roger@gmail.com")
                    .firstName("Roger")
                    .lastName("Sharp")
                    .build());
            service.saveCustomer(Customer.builder().
                    email("amy@gmail.com")
                    .firstName("Amy")
                    .lastName("Smith")
                    .build());
            service.saveCustomer(Customer.builder().
                    email("peter@gmail.com")
                    .firstName("Peter")
                    .lastName("Parker")
                    .build());
        }
    }
}
