package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.entity.Product;
import com.appsdeveloperblog.ws.products.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProductService {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository,
                          KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Long createProduct(Product product) {
        productRepository.save(product);

        ProductCreatedEvent productCreatedEvent =
            new ProductCreatedEvent(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity()
            );

        // will send the message asynchronously
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
            kafkaTemplate.send("${kafka.topic.name}", product.getId().toString(), productCreatedEvent);

        future.whenComplete((result, _ex) -> {
            if (_ex != null) {
                log.error("Failed to send product event", _ex);
            } else {
                log.info("Message sent successfully to topic: {}", result.getRecordMetadata());
            }
        });

        // to make send-message event synchronous, uncomment line below
        //future.join();

        log.info("******* Returning product id");

        return product.getId();
    }
}
