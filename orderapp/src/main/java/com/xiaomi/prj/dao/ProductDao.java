package com.xiaomi.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xiaomi.prj.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {
	List<Product> findByQuantity(int qty); // select * from products where quantity = ?
	
	// select * from products where category = ?
	//List<Product> findByCategory(String category);// assume category is a field in Product
	
	// select * from products where category = ? and quantity = ?
	//List<Product> findByCategoryAndQuantity(String category, int qty); 
	//List<Product> findByCategoryOrQuantity(String category, int qty); 
	
//	@Query(value="select * from products where amount >= :l and amount <= :h", nativeQuery = true)
	@Query("from Product where price >= :l and price <= :h")
	List<Product> getByRange(@Param("l") double low, @Param("h") double high);
	
	@Modifying
	@Query("update Product set price = :p where id = :id")
	void updateProduct(@Param("id") int id, @Param("p") double price);
}
