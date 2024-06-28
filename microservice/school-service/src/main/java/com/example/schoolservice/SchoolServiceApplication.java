package com.example.schoolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SchoolServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolServiceApplication.class, args);
    }

    @LoadBalanced
    public WebClient.Builder webBuilder() {
        return WebClient.builder();
    }
}
