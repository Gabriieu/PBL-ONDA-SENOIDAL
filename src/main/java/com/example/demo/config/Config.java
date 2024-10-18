package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

// Classe de configuração para criar um bean de conexão com o banco de dados
@Configuration
public class Config {

    // Método que cria um bean de conexão com o banco de dados para utilização do JasperReports
    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
