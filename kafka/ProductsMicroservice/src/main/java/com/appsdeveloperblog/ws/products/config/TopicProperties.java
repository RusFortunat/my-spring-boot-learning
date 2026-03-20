package com.appsdeveloperblog.ws.products.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.topic")
public record TopicProperties (
    String name,
    String dltName,
    int partitions,
    int replicas,
    String minInsyncReplicas
) {}
