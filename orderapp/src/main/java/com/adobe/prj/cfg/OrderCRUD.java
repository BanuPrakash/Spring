package com.adobe.prj.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adobe.prj.entity.Item;
import com.adobe.prj.entity.Order;
import com.adobe.prj.service.OrderService;

@Component
public class OrderCRUD implements CommandLineRunner {
	@Autowired
	private OrderService service;
	
	@Override
	public void run(String... args) throws Exception {
//		List<Order> orders = service.getOrders();
//		for(Order o : orders) {
//			System.out.println(o.getOrderId()  + " , " + o.getCustomer().getEmail());
//			// LazyInitialization Exception
//			List<Item> items = o.getItems(); // Proxy ==> trys to make a connection to DB and fetch
//			for(Item i : items) {
//				System.out.println(i.getAmount());
//			}
//			
//		}
//		Order order = new Order();
//		
//		order.setCustomer(Customer.builder().email("b@adobe.com").build());
//		
//		order.getItems().add(Item.builder()
//				.product(Product.builder().id(2).build())
//				.quantity(3)
//				.build());
//		order.getItems().add(Item.builder()
//				.product(Product.builder().id(4).build())
//				.quantity(2)
//				.build());
//		service.placeOrder(order);
	}

}
