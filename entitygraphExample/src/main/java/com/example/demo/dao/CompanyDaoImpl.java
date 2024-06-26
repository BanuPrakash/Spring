package com.example.demo.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Company;

@Repository
public class CompanyDaoImpl implements BaseDao<Company, Long> {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Company findWithGraph(Long id, String graphName) {
		EntityGraph<?> entityGraph = entityManager.getEntityGraph(graphName);
		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", entityGraph);
		//javax.persistence.loadgraph
		Company company = entityManager.find(Company.class, id, hints);
		return company;
	}

}
