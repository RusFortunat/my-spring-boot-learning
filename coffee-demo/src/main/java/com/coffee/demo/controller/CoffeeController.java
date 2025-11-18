package com.coffee.demo.controller;

import com.coffee.demo.entity.Coffee;
import com.coffee.demo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffees")
@Slf4j
public class CoffeeController {

    CoffeeRepository coffeeRepository;

    public CoffeeController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @GetMapping
    public Iterable<Coffee> getCoffees() {
        return this.coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Coffee> getCoffeeById(@PathVariable String id) {
        return this.coffeeRepository.findById(id);
    }

    @PostMapping
    public List<Coffee> addCoffee(@RequestBody List<Coffee> moreCoffees) {
        this.coffeeRepository.saveAll(moreCoffees);
        log.info("More coffees added: {}", moreCoffees);
        return moreCoffees;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coffee> updateCoffee(@PathVariable String id,
                             @RequestBody Coffee coffee) {
        return (!this.coffeeRepository.existsById(id))
            ? new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED)
            : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable String id) {
        this.coffeeRepository.deleteById(id);
    }
}
