package com.example.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/diario");
        ds.setUsername("diario_user");
        ds.setPassword("Secretito ;)");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setMaximumPoolSize(5);
        ds.setMinimumIdle(2);
        ds.setConnectionTimeout(30000);
        ds.setIdleTimeout(600000);
        ds.setMaxLifetime(1800000);

        System.out.println("✅ DataSource creado manualmente para: " + ds.getJdbcUrl());
        return ds;
    }
}