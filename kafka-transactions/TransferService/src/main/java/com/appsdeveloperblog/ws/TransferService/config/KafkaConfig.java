package com.appsdeveloperblog.ws.TransferService.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

@Configuration
public class KafkaConfig {

    Environment environment;

    @Value("${withdraw-money-topic}")
    private String withdrawTopicName;

    @Value("${deposit-money-topic}")
    private String depositTopicName;

    public KafkaConfig(Environment environment) {
        this.environment = environment;
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.key-serializer"));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.value-serializer"));
        props.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.acks"));
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,
            environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        props.put(ProducerConfig.LINGER_MS_CONFIG,
            environment.getProperty("spring.kafka.producer.properties.linger.ms"));
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
            environment.getProperty("spring.kafka.producer.properties.request.timeout.ms"));
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
            environment.getProperty("spring.kafka.producer.properties.enable.idempotence"));
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,
            environment.getProperty("spring.kafka.producer.properties.max.in.flight.requests.per.connection"));
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,
            environment.getProperty("spring.kafka.producer.transaction-id-prefix"));

        return props;
    }

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

    @Bean("kafkaTransactionManager")
    KafkaTransactionManager<String, Object> kafkaTransactionManager() {
        return new KafkaTransactionManager<>(producerFactory());
    }

    @Bean
    NewTopic createWithdrawTopic() {
        return TopicBuilder.name(withdrawTopicName).partitions(3).replicas(3).build();
    }

    @Bean
    NewTopic createDepositTopic() {
        return TopicBuilder.name(depositTopicName).partitions(3).replicas(3).build();
    }
}
