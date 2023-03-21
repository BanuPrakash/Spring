package com.xiaomi.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaomi.prj.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {

}
