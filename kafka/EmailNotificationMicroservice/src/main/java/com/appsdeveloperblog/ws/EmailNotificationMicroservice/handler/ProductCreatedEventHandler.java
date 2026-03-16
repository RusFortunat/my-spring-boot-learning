package com.appsdeveloperblog.ws.EmailNotificationMicroservice.handler;

import com.appsdeveloperblog.ws.EmailNotificationMicroservice.error.NotRetryableException;
import com.appsdeveloperblog.ws.EmailNotificationMicroservice.error.RetryableException;
import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductCreatedEventHandler {

    private RestTemplate restTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductCreatedEventHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics="product-created-events-topic", groupId = "product-created-events")
    public void handle(@Payload ProductCreatedEvent event){

//        if (true) {
//            throw new NotRetryableException("Non-retryable exception is thrown!");
//        }

        if (event == null) {
            // deserialization failed, check headers for the error
            LOGGER.error("Failed to deserialize message");
            return;
        }

//        String theUrl = "http://localhost:8082";
//        try{
//            ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, null, String.class);
//
//            if(response.getStatusCode().is2xxSuccessful()){
//                LOGGER.info("Received reponse body: {}", response.getBody());
//            }
//
//        } catch (ResourceAccessException exception) {
//            LOGGER.error(exception.getMessage());
//            throw new RetryableException(exception.getMessage());
//        } catch (HttpServerErrorException exception) {
//            LOGGER.error(exception.getMessage());
//            throw new NotRetryableException(exception.getMessage());
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//            throw new NotRetryableException(e.getMessage());
//        }

        LOGGER.info("Received a new event: " +  event.getTitle());

    }
}