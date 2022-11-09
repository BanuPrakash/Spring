package com.adobe.prj;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;

// database, redis, shortenUrl, RabbitMQ
@Component("fetch-products-health")
public class FetchProductsHealthContributor implements CompositeHealthContributor {

	private Map<String, HealthContributor> contributors = new LinkedHashMap<>();

	@Autowired
	public FetchProductsHealthContributor(DatabaseHealthContributor databaseHealthContributor) {
		super();
		// Check if USERS table used in the API can be queried with 
		//Health Indicator of Database
		contributors.put("database", databaseHealthContributor);
	}

	/**
	 * return list of health contributors
	 */
	@Override
	public Iterator<NamedContributor<HealthContributor>> iterator() {
		return contributors.entrySet().stream().map((entry) -> NamedContributor.of(entry.getKey(), entry.getValue()))
				.iterator();
	}

	@Override
	public HealthContributor getContributor(String name) {
		return contributors.get(name);
	}
}
