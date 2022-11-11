package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Customer;
import com.adobe.prj.service.CustomerService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	CustomerService service;
	
	@GetMapping
	public @ResponseBody List<Customer> getCustomers() {
		return service.loadAllCustomers();
	}
	
	@GetMapping(value="/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> getByStream() {
		return service.loadAllCustomersStream();
	}
}
