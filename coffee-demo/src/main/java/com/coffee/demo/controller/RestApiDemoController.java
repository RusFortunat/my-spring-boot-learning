package com.coffee.demo.controller;

import com.coffee.demo.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/coffees")
@Slf4j
public class RestApiDemoController {

    private final List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController() {
        coffees.addAll(List.of(
           new Coffee("Arabica"),
           new Coffee("Robusta")
        ));
    }

    @GetMapping
    public Iterable<Coffee> getCoffees() {
        return coffees;
    }

    @GetMapping("/{id}")
    public Optional<Coffee> getCoffeeById(@PathVariable String id) {
        return coffees.stream()
            .filter(coffee -> coffee.getId().equals(id))
            .findAny();
    }

    @PostMapping
    public List<Coffee> addCoffee(@RequestBody List<Coffee> moreCoffees) {
        this.coffees.addAll(moreCoffees);
        log.info("More coffees added: {}", moreCoffees);
        return moreCoffees;
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Coffee>> updateCoffee(@PathVariable String id,
                             @RequestBody Coffee coffee) {
        Optional<Coffee> coffeePresent = coffees.stream()
            .filter(c -> c.getId().equals(id))
            .findAny();
        if(coffeePresent.isPresent()) {
            String oldName = coffeePresent.get().getName();
            coffeePresent.get().setName(coffee.getName());
            log.info("Coffee name updated from {} to {}", oldName, coffee.getName());
            return ResponseEntity.ok(List.of(coffee));
        } else {
            log.info("Coffee created: {}", coffee.getName());
            return new ResponseEntity<>(addCoffee(List.of(coffee)), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@RequestBody Coffee coffee) {
        Optional<Coffee> coffeeToDelete = coffees.stream()
            .filter(c -> c.getId().equals(coffee.getId()))
            .findAny();
        if (coffeeToDelete.isPresent()) {
            coffees.remove(coffeeToDelete.get());
            log.info("Coffee deleted: {}", coffee.getName());
        } else {
            log.info("Coffee requested for deletion is not found: {} ", coffee.getName());
        }
    }
}
