package com.coffee.demo.config;

import com.coffee.demo.repository.CoffeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DBIntegration
public class DataLoaderIntegrationTest {

    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void loadData() {

        //long count = coffeeRepository.count();
//        assertThat(this.coffeeRepository.count()).isEqualTo(0);
//        applicationEventPublisher.publishEvent(new ApplicationReadyEvent(
//            new SpringApplication(), null, null, null));

        assertThat(this.coffeeRepository.count()).isEqualTo(2);
    }

}
