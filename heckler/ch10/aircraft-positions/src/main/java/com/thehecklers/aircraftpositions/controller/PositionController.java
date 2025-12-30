package com.thehecklers.aircraftpositions.controller;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import com.thehecklers.aircraftpositions.service.PositionRetriever;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PositionController {
    private final PositionRetriever retriever;

    @GetMapping("/aircraft")
    public Iterable<Aircraft> getCurrentAircraftPositions() {
        return retriever.retrieveAircraftPositions("aircraft");
    }

    @GetMapping("/aircraftadmin")
    public Iterable<Aircraft> getCurrentAircraftPositionsAdmin() {
        return retriever.retrieveAircraftPositions("aircraftadmin");
    }
}
