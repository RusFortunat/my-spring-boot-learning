package com.thehecklers.planefinder.repository;

import com.thehecklers.planefinder.entity.Aircraft;
import org.springframework.data.repository.CrudRepository;

public interface PlaneRepository extends CrudRepository<Aircraft, Long> {
}
