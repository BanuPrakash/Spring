package com.xiaomi.prj.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xiaomi.prj.entity.Customer;
import com.xiaomi.prj.entity.Item;
import com.xiaomi.prj.entity.Order;
import com.xiaomi.prj.entity.Product;
import com.xiaomi.prj.service.OrderService;

@Component
public class OrderClient implements CommandLineRunner {
	@Autowired
	private OrderService service;
	
	@Override
	public void run(String... args) throws Exception {
//		placeOrder();
//		getOrders();
	}

	private void getOrders() {
		List<Order> orders = service.getOrders();
		for(Order o : orders) {
			System.out.println(o.getOid() + "," + o.getCustomer().getEmail() + ", " +o.getTotal());
			System.out.println("Items:");
			for(Item i : o.getItems()) {
				System.out.println(i);
			}
			System.out.println("*****");
		}
	}

	private void placeOrder() {
		Order order = new Order(); 
		Customer c = Customer.builder().email("smith@gmail.com").build();
		order.setCustomer(c);
		
		List<Item> items = Arrays.asList(
				Item.builder().product(Product.builder().id(2).build()).qty(1).build(),
				Item.builder().product(Product.builder().id(3).build()).qty(3).build()
				);
		
		order.setItems(items);
	
		service.placeOrder(order);
	}

}
