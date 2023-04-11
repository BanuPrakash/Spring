package com.example.prj;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class CustomerDao {
	static void sleepExecution(int i) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// java 8 stream
	public List<Customer> getCustomers() {
		return IntStream.rangeClosed(1, 10)
					.peek(CustomerDao::sleepExecution)
					.peek(i -> System.out.println("processing " + i))
					.mapToObj(i -> new Customer(i, "Customer " + i))
					.collect(Collectors.toList());
	}
	
	// flux 0-n publisher
	public Flux<Customer> getCustomerStream() {
		return Flux.range(1, 10)
					.delayElements(Duration.ofSeconds(1))
					.doOnNext( i -> System.out.println("processing count " + i))
					.map(i -> new Customer(i, "Customer " + i));
	}
}
