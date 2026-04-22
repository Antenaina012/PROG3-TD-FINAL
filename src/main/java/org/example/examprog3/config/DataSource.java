package org.example.examprog3.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSource {

    @Bean
    public javax.sql.DataSource dataSource() {
        Dotenv dotenv = Dotenv.load();
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(dotenv.get("DB_URL"));
        ds.setUsername(dotenv.get("DB_USER"));
        ds.setPassword(dotenv.get("DB_PASSWORD"));

        return ds;
    }

    @Bean
    public Connection getConnection(javax.sql.DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}