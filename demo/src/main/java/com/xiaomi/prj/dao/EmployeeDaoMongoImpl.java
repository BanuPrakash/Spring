package com.xiaomi.prj.dao;

import org.springframework.stereotype.Repository;


@Repository("mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("Mongo Store....");
	}

}
