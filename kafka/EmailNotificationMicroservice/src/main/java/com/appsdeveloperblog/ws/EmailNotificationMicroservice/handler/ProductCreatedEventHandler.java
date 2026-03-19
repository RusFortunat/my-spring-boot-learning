package com.appsdeveloperblog.ws.EmailNotificationMicroservice.handler;

import com.appsdeveloperblog.ws.EmailNotificationMicroservice.entity.ProcessedEventEntity;
import com.appsdeveloperblog.ws.EmailNotificationMicroservice.error.NotRetryableException;
import com.appsdeveloperblog.ws.EmailNotificationMicroservice.repository.ProcessedEventRepository;
import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductCreatedEventHandler {

    private RestTemplate restTemplate;
    private final ProcessedEventRepository processedEventRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductCreatedEventHandler(RestTemplate restTemplate,
                                      ProcessedEventRepository processedEventRepository) {
        this.restTemplate = restTemplate;
        this.processedEventRepository = processedEventRepository;
    }

    @KafkaListener(topics="product-created-events-topic", groupId = "product-created-events")
    public void handle(@Payload ProductCreatedEvent event,
                       @Header("messageId") byte[] messageId,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String messageKey) {

        // check if message was processed before
        String messageIdStr = new String(messageId);
        boolean isMessageProcessed = processedEventRepository.existsByMessageId(messageIdStr);
        if(isMessageProcessed) {
            LOGGER.info("Message with id " + messageIdStr + " is already processed");
            return;
        }

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

        // avoid processing the Kafka messages twice
        try {
            processedEventRepository.save(new ProcessedEventEntity(messageIdStr, event.getId().toString()));
        } catch (DataIntegrityViolationException e) {
            throw new NotRetryableException(e);
        }
    }
}