package com.example.shopapp.dao;

import com.example.shopapp.dto.Report;
import com.example.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    @Query("select c.email, c.firstName, c.lastName, o.orderDate, o.total from Order o join o.customer c")
    List<Report> getReport();

    @Query("select new com.example.shopapp.dto.Report(c.email, c.firstName, c.lastName, o.orderDate, o.total) from Order o join o.customer c where date(o.orderDate) = date(:date)")
    List<Report> getReportForDate(@Param("date") Date date);
}
