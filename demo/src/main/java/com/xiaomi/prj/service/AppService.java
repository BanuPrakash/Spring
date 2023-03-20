package com.xiaomi.prj.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaomi.prj.dao.EmployeeDao;

@Service
public class AppService {
	@Autowired
	private EmployeeDao employeeDao; // program to interface ==> Loose Coupling
	
//	@Autowired
//	DataSource ds;
	
	public void insert() {
//		System.out.println(ds);
		this.employeeDao.addEmployee();
	}
}
