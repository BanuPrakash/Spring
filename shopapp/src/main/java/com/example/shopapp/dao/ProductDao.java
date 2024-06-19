package com.example.shopapp.dao;

import com.example.shopapp.dto.ProductRecord;
import com.example.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    /* Examples of Entity Projections */
    // select * from products where quantity = ?
    List<Product> findByQuantity(int qty);
    // select * from products where name in  (a,b,c)
    List<Product> findByNameIn(String[] names);
    // select * from products where name like %name%
    List<Product> findByNameLike(String name);
    // // select * from products where price between = ? and ?
//    List<Product> findByPriceBetween(double low, double high);
    List<Product> findByPriceAndName(double price, String name);
    List<Product> findByPriceGreaterThan(double price);
    // Scalar Projection
    @Query("select name, price from Product") // JP-QL
   // @Query( value = "select name, amount from  products", nativeQuery = true) // SQL
    List<Object[]> getNameAndPrice(); // Avoid this

    @Query("select name, price from Product where price >= :l and price <= :h")
    List<ProductRecord> getByRange(@Param("l") double low, @Param("h") double high);

    List<Product> findByPriceBetween(double low, double high);

    @Modifying
    @Query("update Product set price = :pr where id = :id")
    void updateProduct(@Param("pr") double price, @Param("id") int id);
}
