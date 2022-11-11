package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}