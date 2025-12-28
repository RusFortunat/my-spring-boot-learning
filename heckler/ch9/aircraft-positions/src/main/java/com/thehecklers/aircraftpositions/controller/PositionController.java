package com.thehecklers.aircraftpositions.controller;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import com.thehecklers.aircraftpositions.repository.AircraftRepository;
import com.thehecklers.aircraftpositions.service.PositionRetrieverService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
public class PositionController {

    private final PositionRetrieverService positionRetrieverService;

    @GetMapping("/aircraft")
    public Iterable<Aircraft> getCurrentAircraftPositions(Model model) {
        return positionRetrieverService.retrieveAircraftPositions();
    }
}
