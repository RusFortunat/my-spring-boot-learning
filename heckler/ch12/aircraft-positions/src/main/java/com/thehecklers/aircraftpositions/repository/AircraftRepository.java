package com.thehecklers.aircraftpositions.repository;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AircraftRepository extends ReactiveCrudRepository<Aircraft, Long> {

    Flux<Aircraft> findAircraftByReg(String reg);
}
