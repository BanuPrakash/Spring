package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {
	// select * from customers where lastName = ?
	List<Customer> findByLastName(String lastName);
	// select * from customers where firstName = ? and lastName = ?
	List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
	// select * from customers where firstName = ? OR lastName = ?
	List<Customer> findByFirstNameOrLastName(String firstName, String lastName);
}
