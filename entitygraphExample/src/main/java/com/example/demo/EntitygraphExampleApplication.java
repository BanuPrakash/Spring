package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.CompanyDao;
import com.example.demo.entity.Company;

@SpringBootApplication
public class EntitygraphExampleApplication  implements CommandLineRunner {

	@Autowired
	CompanyDao companyDao;
	
	public static void main(String[] args) {
		SpringApplication.run(EntitygraphExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Company company = companyDao.getById(1);
		System.out.println(company.getName());
		company.getDepartments().stream().forEach(d -> System.out.println(d.getName()));
	}

}
