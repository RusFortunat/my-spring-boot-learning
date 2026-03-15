package com.appsdeveloperblog.ws.EmailNotificationMicroservice.handler;

import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics="product-created-events-topic", groupId = "product-created-events")
    public void handle(@Payload ProductCreatedEvent event){

//        throw new RuntimeException("Test DLT routing");

        if (event == null) {
            // deserialization failed, check headers for the error
            LOGGER.error("Failed to deserialize message");
            return;
        }

        LOGGER.info("Received a new event: " +  event.getTitle());

    }
}