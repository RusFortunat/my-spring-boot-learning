package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import com.appsdeveloperblog.ws.products.config.TopicProperties;
import com.appsdeveloperblog.ws.products.entity.Product;
import com.appsdeveloperblog.ws.products.repository.ProductRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProductService {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final ProductRepository productRepository;
    private final TopicProperties topicProperties;

    public ProductService(ProductRepository productRepository,
                          KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate,
                          TopicProperties topicProperties) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topicProperties = topicProperties;
    }

    @SneakyThrows
    public Long createProduct(Product product) throws Exception {
        productRepository.save(product);

        ProductCreatedEvent productCreatedEvent =
            new ProductCreatedEvent(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity()
            );

        // will send the message asynchronously
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
//            kafkaTemplate.send(topicProperties.name(), product.getId().toString(), productCreatedEvent);
//
//        future.whenComplete((result, _ex) -> {
//            if (_ex != null) {
//                log.error("Failed to send product event", _ex);
//            } else {
//                log.info("Message sent successfully.");
//            }
//        });

        // synchronous variant
        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
            topicProperties.name(),
            product.getId().toString(),
            productCreatedEvent
        );
        record.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        SendResult<String, ProductCreatedEvent> result =
            kafkaTemplate.send(topicProperties.name(), product.getId().toString(), productCreatedEvent).get();

        log.info("New Product was sent to:");
        log.info("Topic: {}", result.getRecordMetadata().topic());
        log.info("Partition: {}", result.getRecordMetadata().partition());
        log.info("Offset: {}", result.getRecordMetadata().offset());

        log.info("******* Returning product id");

        return product.getId();
    }
}
