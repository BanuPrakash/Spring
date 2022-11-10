package com.adobe.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adobe.entity.EmployeeAddresses;
import com.adobe.entity.EmployeeNames;
import com.adobe.entity.EmployeePhone;

@Service
public class AsyncService {

	private static Logger log = LoggerFactory.getLogger(AsyncService.class);

	@Autowired
	private RestTemplate restTemplate;

	

	@Async("asyncExecutor")
	public CompletableFuture<EmployeeNames> getEmployeeName() throws InterruptedException {
		log.info("getEmployeeName Starts");
		EmployeeNames employeeNameData = restTemplate.getForObject("http://localhost:8080/names", EmployeeNames.class);

		log.info("employeeNameData, {}", employeeNameData);
		Thread.sleep(1000L); // Intentional delay
		log.info("employeeNameData completed");
		return CompletableFuture.completedFuture(employeeNameData);
	}

	@Async("asyncExecutor")
	public CompletableFuture<EmployeeAddresses> getEmployeeAddress() throws InterruptedException {
		log.info("getEmployeeAddress Starts");
		EmployeeAddresses employeeAddressData = restTemplate.getForObject("http://localhost:8080/address",
				EmployeeAddresses.class);

		log.info("employeeAddressData, {}", employeeAddressData);
		Thread.sleep(1000L); // Intentional delay
		log.info("employeeAddressData completed");
		return CompletableFuture.completedFuture(employeeAddressData);
	}

	@Async("asyncExecutor")
	public CompletableFuture<EmployeePhone> getEmployeePhone() throws InterruptedException {
		log.info("getEmployeePhone Starts");
		EmployeePhone employeePhoneData = restTemplate.getForObject("http://localhost:8080/phones", EmployeePhone.class);

		log.info("employeePhoneData, {}", employeePhoneData);
		Thread.sleep(1000L); // Intentional delay
		log.info("employeePhoneData completed");
		return CompletableFuture.completedFuture(employeePhoneData);
	}

}
