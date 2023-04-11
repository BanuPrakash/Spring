package com.example.prj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	CustomerService service;
	
	@GetMapping
	public List<Customer> getCustomers() {
		return service.loadAllCustomers();
	}
	// SSE
	@GetMapping(value="/stream" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> getStream() {
		return service.loadAllCustomersFlux();
	}
}
