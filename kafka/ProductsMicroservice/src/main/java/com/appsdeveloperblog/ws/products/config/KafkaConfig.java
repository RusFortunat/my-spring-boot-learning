package com.appsdeveloperblog.ws.products.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic createTopic(TopicProperties props) {
        return TopicBuilder
            .name(props.name())
            .partitions(props.partitions())
            .replicas(props.replicas())
            .configs(Map.of("min.insync.replicas", props.minInsyncReplicas()))
            .build();
    }

}
