package com.appsdeveloperblog.ws.products.config;

import com.appsdeveloperblog.ws.products.service.ProductCreatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

//    Map<String, Object> producerConfigs() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.bootstrapServers());
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.keySerializer());
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.valueSerializer());
//        config.put(ProducerConfig.ACKS_CONFIG, producerProperties.acks());
//        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, producerProperties.propertiesDeliveryTimeoutMs());
//        config.put(ProducerConfig.LINGER_MS_CONFIG, producerProperties.propertiesLingerMs());
//        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, producerProperties.propertiesRequestTimeoutMs());
//
//        return config;
//    }
//
//    @Bean
//    ProducerFactory<String, ProductCreatedEvent> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }

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
