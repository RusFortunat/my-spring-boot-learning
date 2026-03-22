package com.appsdeveloperblog.ws.TransferService.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name="transfers")
public class TransferEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private String transferId;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String recipientId;

    @Column(nullable = false)
    private BigDecimal amount;
}
