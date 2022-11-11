package com.adobe.prj.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adobe.prj.dao.ProductRepository;
import com.adobe.prj.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(Product product) {
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
