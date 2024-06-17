package com.example.shopapp.dao;

import com.example.shopapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, String> {
}
