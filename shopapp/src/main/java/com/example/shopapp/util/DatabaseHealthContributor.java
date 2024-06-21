package com.example.shopapp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component("MySQLDatabaseProductTable")
@RequiredArgsConstructor
public class DatabaseHealthContributor  implements HealthIndicator, HealthContributor {
    private final DataSource ds;
    @Override
    public Health health() {
        try(Connection con = ds.getConnection()) {
            Statement statement = con.createStatement();
            statement.executeQuery("select  * from products");
            return Health.up().build();
        } catch (SQLException ex) {
            return Health.outOfService().withException(ex).build();
        }
    }
}
