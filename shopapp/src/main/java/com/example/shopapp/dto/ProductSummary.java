package com.example.shopapp.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ProductSummary {
    @Value("#{target.name + \", \" + target.price}") // SPeL
    String getNameAndPrice();
}

// ProductSummary findByName(String name); // DAO