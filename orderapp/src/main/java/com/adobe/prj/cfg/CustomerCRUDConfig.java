package com.adobe.prj.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adobe.prj.dao.CustomerDao;
import com.adobe.prj.entity.Customer;

@Component
public class CustomerCRUDConfig  implements CommandLineRunner {
	@Autowired
	CustomerDao customerDao;
	
	@Override
	public void run(String... args) throws Exception {
		if(customerDao.count() == 0) {
			customerDao.save(Customer.builder()
					 .email("a@adobe.com")
					.firstName("Ashok")
					.lastName("Sharma").build());
			customerDao.save(Customer.builder()
					 .email("b@adobe.com")
					.firstName("Beena")
					.lastName("Patil").build());
		}
	}

}
