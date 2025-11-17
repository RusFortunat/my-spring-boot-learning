package com.coffee.demo.config;

import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Testcontainers
@Import(PostgresTestContainerConfig.class)
public @interface DBIntegration {
}
