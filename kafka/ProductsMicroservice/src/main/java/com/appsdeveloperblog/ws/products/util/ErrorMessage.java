package com.appsdeveloperblog.ws.products.util;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ErrorMessage {

    private OffsetDateTime timestamp;
    private String message;
    private String details;

    public ErrorMessage (OffsetDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
