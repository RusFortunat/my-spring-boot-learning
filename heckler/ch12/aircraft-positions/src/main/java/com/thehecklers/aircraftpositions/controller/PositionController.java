package com.thehecklers.aircraftpositions.controller;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import com.thehecklers.aircraftpositions.repository.AircraftRepository;
import com.thehecklers.aircraftpositions.service.PositionService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class PositionController {
    private final RSocketRequester requester;
    private final PositionService positionService;

    public PositionController(RSocketRequester requester,
                              PositionService positionService) {
        this.requester = requester;
        this.positionService = positionService;
    }

    // HTTP endpoint, HTTP requester (previously created)
    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model) {
        model.addAttribute("currentPositions", positionService.getCurrentAircraftPositions());
        return "positions";
    }

    @ResponseBody
    @GetMapping("/acpos")
    public Flux<Aircraft> getACPositions() {
        return positionService.getCurrentAircraftPositions();
    }

    // HTTP endpoint, RSocket client endpoint
    @ResponseBody
    @GetMapping(value = "/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentACPositionsStream() {
        return requester.route("acstream")
                .data("Requesting aircraft positions")
                .retrieveFlux(Aircraft.class);
    }

    @ResponseBody
    @GetMapping("/acpos/search")
    public Publisher<Aircraft> searchForACPosition(@RequestParam Map<String, String> searchParams) {
        if (!searchParams.isEmpty()){
           Map.Entry<String, String> setToSearch = searchParams.entrySet().iterator().next();
           if (setToSearch.getKey().equalsIgnoreCase("id")){
               return positionService.getACPositionById(Long.parseLong(setToSearch.getValue()));
           } else {
               return positionService.getACPositionByRegistration(setToSearch.getValue());
           }
        } else {
            return Mono.empty();
        }
    }
}