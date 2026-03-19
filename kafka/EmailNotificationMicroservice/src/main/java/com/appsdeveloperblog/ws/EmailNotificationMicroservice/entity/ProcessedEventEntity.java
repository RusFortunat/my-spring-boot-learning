package com.appsdeveloperblog.ws.EmailNotificationMicroservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="processed_events")
@Data
@NoArgsConstructor
public class ProcessedEventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, unique=true)
    private String messageId;

    @Column(nullable=false)
    private String productId;

    public ProcessedEventEntity(String messageId, String productId) {
        this.messageId = messageId;
        this.productId = productId;
    }

}
