package com.coffee.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "greeting")
@Getter
@Setter
public class Greeting {
    
    private String name;
    private String coffee;

}
