package com.xiaomi.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaomi.prj.entity.Order;
import com.xiaomi.prj.service.OrderService;

@RestController
@RequestMapping("api/orders")
public class OrderController {
	@Autowired
	private OrderService service;
	
	@GetMapping()
	public List<Order> getOrders() {
		return service.getOrders();
	}
	
	@PostMapping()
	public String placeOrder(@RequestBody Order o) {
		service.placeOrder(o); // cascade takes care of saving items also,
		// dirty checking takes care of updating products --> quantity
		return "order placed!!!";
	}
}
