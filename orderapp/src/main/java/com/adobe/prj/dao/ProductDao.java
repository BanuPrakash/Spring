package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.Product;
// no need to create @Repository class -> Spring Data JPA generates it
public interface ProductDao extends JpaRepository<Product, Integer> {

}
