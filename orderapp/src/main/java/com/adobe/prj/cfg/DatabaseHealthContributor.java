package com.adobe.prj;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("database")
public class DatabaseHealthContributor implements HealthIndicator, HealthContributor {
	@Autowired
	DataSource ds;
	
	@Override
	public Health health() {
		try(Connection con = ds.getConnection()) {
			Statement stmt = con.createStatement();
			stmt.executeQuery("select name, price from products");
		} catch(SQLException ex) {
			return Health.outOfService().withException(ex).build();
		}
		return Health.up().build();
	}
 
}
