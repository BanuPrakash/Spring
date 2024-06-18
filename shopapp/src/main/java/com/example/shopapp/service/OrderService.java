package com.example.shopapp.service;

import com.example.shopapp.dao.CustomerDao;
import com.example.shopapp.dao.ProductDao;
import com.example.shopapp.dto.ProductRecord;
import com.example.shopapp.entity.Customer;
import com.example.shopapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductDao productDao;
    private  final CustomerDao customerDao;

    public long getProductCount() {
        return productDao.count();
    }
    public long getCustomerCount() {
        return customerDao.count();
    }

    public Customer findCustomerByEmail(String email) {
        return customerDao.findById(email).get();
    }

    public Product findProductById(int id) {
        Optional<Product> optionalProduct = productDao.findById(id);
        if(optionalProduct.isPresent()) {
            return  optionalProduct.get();
        }
        return null; // later change to Exception
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product saveProduct(Product p) {
        return  productDao.save(p);
    }

    public Customer saveCustomer(Customer c) {
        return  customerDao.save(c);
    }

    public List<ProductRecord> getByRange(double low, double high) {
        return productDao.getByRange(low, high);
    }
}
