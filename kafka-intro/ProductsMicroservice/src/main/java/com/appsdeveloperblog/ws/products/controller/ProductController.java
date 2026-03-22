package com.appsdeveloperblog.ws.products.controller;

import com.appsdeveloperblog.ws.products.entity.Product;
import com.appsdeveloperblog.ws.products.service.ProductService;
import com.appsdeveloperblog.ws.products.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {

        Long productId;
        try {
            productId = productService.createProduct(product);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder()
                    .timestamp(OffsetDateTime.now())
                    .message(ex.getMessage())
                    .details("/products")
                    .build());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productId.toString());
    }

}
