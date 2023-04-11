package com.example.prj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {
	@Autowired
	CustomerDao dao;

	public List<Customer> loadAllCustomers() {
		long start = System.currentTimeMillis();

		List<Customer> customers = dao.getCustomers(); //blocking
		long end = System.currentTimeMillis();

		System.out.println("Total execution time " + (end - start) + " ms");
		return customers;
	}
	

	public Flux<Customer> loadAllCustomersFlux() {
		long start = System.currentTimeMillis();

		Flux<Customer> customers = dao.getCustomerStream();
		long end = System.currentTimeMillis();

		System.out.println("Total execution time " + (end - start) + " ms");
		return customers;
	}
}
