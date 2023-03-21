package com.xiaomi.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaomi.prj.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
