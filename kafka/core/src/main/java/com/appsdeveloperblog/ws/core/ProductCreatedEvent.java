package com.appsdeveloperblog.ws.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// @NoArgsConstructor is needed for deserialisation
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCreatedEvent {

    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantity;


}
