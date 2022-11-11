package com.adobe.prj.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adobe.prj.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}	
