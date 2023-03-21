package com.xiaomi.prj.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaomi.prj.dao.ProductDao;
import com.xiaomi.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	public List<Product> getProducts() {
		return productDao.findAll(); 
	}
	
	public Product getProductById(int id) {
		Optional<Product> optProduct =  productDao.findById(id);
		if(optProduct.isPresent()) {
			return optProduct.get();
		}
		else return null;
	}
	
	public Product addProduct(Product p) {
		return productDao.save(p); //returned product will have GENERATED PK value
	}
	
	public List<Product> getByRange(double low, double high) {
		return productDao.getByRange(low, high);
	}
	
	@Transactional
	public Product updateProduct(int id, double price) {
		productDao.updateProduct(id, price);
		return this.getProductById(id);
	}
}
