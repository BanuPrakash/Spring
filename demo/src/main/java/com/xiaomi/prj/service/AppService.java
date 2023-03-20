package com.xiaomi.prj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaomi.prj.dao.EmployeeDao;

@Service
public class AppService {
	@Autowired
	private EmployeeDao employeeDao; // program to interface ==> Loose Coupling
	
	public void insert() {
		this.employeeDao.addEmployee();
	}
}
