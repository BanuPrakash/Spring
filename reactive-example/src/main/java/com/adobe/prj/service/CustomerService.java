package com.adobe.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.adobe.prj.dao.CustomerDao;
import com.adobe.prj.entity.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
	@Autowired
	CustomerDao customerDao;
	
	public List<Customer> loadAllCustomers() {
		long start = System.currentTimeMillis();
			List<Customer> customers = customerDao.getCustomers();
		long end = System.currentTimeMillis();
		System.out.println("Total time List<Customer> : " + (end - start) + " ms");
		return customers;
	}
	
	public Flux<Customer> loadAllCustomersStream() {
		customerDao.getCustomersStream().subscribe(c -> System.out.println(c));
		long start = System.currentTimeMillis();
			Flux<Customer> customers = customerDao.getCustomersStream();
		long end = System.currentTimeMillis();
		System.out.println("Total time Flux<Customer> : " + (end - start) + " ms");
		return customers;
	}
	
	// HandlerFunction for Functional Web Frameowrk --> Routes
	public Mono<ServerResponse> handleLoadCustomers(ServerRequest request) {
		Flux<Customer> customers = customerDao.getCustomersStream();
		return ServerResponse.ok().body(customers, Customer.class);
	}
	
}
