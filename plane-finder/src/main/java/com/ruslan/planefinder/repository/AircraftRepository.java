package com.ruslan.planefinder.repository;

import com.ruslan.planefinder.entity.Aircraft;
import org.springframework.data.repository.CrudRepository;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {}
