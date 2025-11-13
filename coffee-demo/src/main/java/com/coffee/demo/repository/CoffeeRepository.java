package com.coffee.demo.repository;

import com.coffee.demo.entity.Coffee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, String> {
}
