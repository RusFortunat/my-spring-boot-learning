package com.appsdeveloperblog.ws.EmailNotificationMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EmailNotificationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailNotificationMicroserviceApplication.class, args);
	}

}
