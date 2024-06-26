package com.example.demo.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Company;

public interface CompanyDao extends JpaRepository<Company, Long>{
	
//	@EntityGraph(type=EntityGraphType.FETCH, value="companyWithDepartmentsGraph")
	@EntityGraph(type=EntityGraphType.FETCH, value="companyWithDepartmentsAndEmployeesAndOfficesGraph")
	Company getById(@Param("id") long id) ;
	
	
}
