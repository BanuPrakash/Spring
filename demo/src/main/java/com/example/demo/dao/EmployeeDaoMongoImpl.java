package com.example.demo.dao;

import com.example.demo.model.Employee;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
//@ConditionalOnProperty(name = "dao", havingValue = "mongo")
@ConditionalOnMissingBean(EmployeeDaoJdbcImpl.class)
public class EmployeeDaoMongoImpl implements  EmployeeDao{
    @Override
    public void addEmployee(Employee employee) {
        System.out.println("Mongo Store!!!!");
    }
}
