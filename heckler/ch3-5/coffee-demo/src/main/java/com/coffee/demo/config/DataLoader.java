package com.coffee.demo.config;

import com.coffee.demo.entity.Coffee;
import com.coffee.demo.repository.CoffeeRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void loadData() {
        this.coffeeRepository.saveAll(List.of(
            new Coffee("Arabica"),
            new Coffee("Robusta")
        ));
    }
}
