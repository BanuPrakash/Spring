package com.example.demo.dao;

import com.example.demo.model.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class EmployeeDaoJdbcImpl implements  EmployeeDao{
    @Override
    public void addEmployee(Employee employee) {
        System.out.println("added to RDBMS!!!");
    }
}
