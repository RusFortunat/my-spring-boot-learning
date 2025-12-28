package com.coffee.demo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Configuration
@Testcontainers
public class PostgresTestContainerConfig {

    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer =
        new PostgreSQLContainer<>("postgres:16")
        .withExposedPorts(5432)
        .withUsername("postgres")
        .withPassword("postgres")
        .withDatabaseName("testdb");

    static {
        postgreSQLContainer.start();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(postgreSQLContainer.getJdbcUrl())
            .username(postgreSQLContainer.getUsername())
            .password(postgreSQLContainer.getPassword())
            .driverClassName(postgreSQLContainer.getDriverClassName())
            .build();
    }

//    @DynamicPropertySource
//    static void dynamicProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.driver_class", postgreSQLContainer::getDriverClassName);
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
//    }
}
