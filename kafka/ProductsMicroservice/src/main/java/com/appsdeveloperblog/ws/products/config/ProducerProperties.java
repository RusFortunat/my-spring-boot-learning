package com.appsdeveloperblog.ws.products.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.producer")
public record ProducerProperties (
    String bootstrapServers,
    String keySerializer,
    String valueSerializer,
    String acks,
    String deliveryTimeoutMs,
    String lingerMs,
    String requestTimeoutMs
) {}