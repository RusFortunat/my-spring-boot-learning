package com.coffee.demo.config;

import com.coffee.demo.entity.Coffee;
import com.coffee.demo.repository.CoffeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @PostConstruct
    private void loadData() {
        this.coffeeRepository.saveAll(List.of(
            new Coffee("Arabica"),
            new Coffee("Robusta")
        ));
    }
}
