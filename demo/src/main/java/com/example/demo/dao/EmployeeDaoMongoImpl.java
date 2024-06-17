package com.example.demo.dao;

import com.example.demo.model.Employee;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class EmployeeDaoMongoImpl implements  EmployeeDao{
    @Override
    public void addEmployee(Employee employee) {
        System.out.println("Mongo Store!!!!");
    }
}
