package com.thehecklers.aircraftpositions.service;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import com.thehecklers.aircraftpositions.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PositionRetrieverService {

    private final AircraftRepository repository;
    private WebClient client =
        WebClient.create("http://localhost:7634/aircraft");

    public Iterable<Aircraft> retrieveAircraftPositions() {
        repository.deleteAll();

        client.get()
            .retrieve()
            .bodyToFlux(Aircraft.class)
            .filter(plane -> !plane.getReg().isEmpty())
            .toStream()
            .forEach(repository::save);

        return repository.findAll();
    }

}
