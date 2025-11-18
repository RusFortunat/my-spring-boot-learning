package com.coffee.demo.config;

import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Testcontainers
@Import(PostgresTestContainerConfig.class)
public @interface DBIntegration {
}
