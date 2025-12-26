package com.ruslan.planefinder.repository;

import com.ruslan.planefinder.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

}
