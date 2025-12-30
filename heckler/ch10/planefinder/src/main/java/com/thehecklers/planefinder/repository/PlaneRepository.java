package com.thehecklers.planefinder.repository;

import com.thehecklers.planefinder.entity.Aircraft;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaneRepository extends ReactiveCrudRepository<Aircraft, Long> {
}
