package com.xiaomi.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaomi.prj.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

}
