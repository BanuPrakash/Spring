package com.example.prj.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prj.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {

}
