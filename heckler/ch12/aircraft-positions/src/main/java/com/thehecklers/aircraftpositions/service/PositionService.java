package com.thehecklers.aircraftpositions.service;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import com.thehecklers.aircraftpositions.repository.AircraftRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PositionService {

    private WebClient client =
        WebClient.create("http://localhost:7634/aircraft");
    private final AircraftRepository repository;

    public PositionService(AircraftRepository repository) {
        this.repository = repository;
    }

    public Flux<Aircraft> getCurrentAircraftPositions() {
        return repository.deleteAll()
            .thenMany(client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .flatMap(repository::save));

    }

    public Mono<Aircraft> getACPositionById(Long id) {
        return repository.findById(id);
    }

    public Flux<Aircraft> getACPositionByRegistration(String reg) {
        return repository.findAircraftByReg(reg);
    }
}
