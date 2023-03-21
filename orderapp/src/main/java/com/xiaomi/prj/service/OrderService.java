package com.xiaomi.prj.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaomi.prj.dao.OrderDao;
import com.xiaomi.prj.dao.ProductDao;
import com.xiaomi.prj.entity.Item;
import com.xiaomi.prj.entity.Order;
import com.xiaomi.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDao orderDao;
	
	public List<Order> getOrders() {
		return orderDao.findAll();
	}
	
	@Transactional
	public String placeOrder(Order o) {
		double total = 0.0;
		
		List<Item> items = o.getItems();
		for(Item item : items) {
			Product p = productDao.findById(item.getProduct().getId()).get();
			if(p.getQuantity() - item.getQty() < 0) {
				throw new IllegalArgumentException("Product not is Stock!!!");
			}
			item.setAmount(p.getPrice() * item.getQty()); // tax, coupon
			p.setQuantity(p.getQuantity() - item.getQty()); // dirty checking causes UPDATE ==> no explicit update required
			total += item.getAmount();
		}
		
		o.setTotal(total);
		orderDao.save(o);
		
		return "Order placed!!!";
	}
	
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
