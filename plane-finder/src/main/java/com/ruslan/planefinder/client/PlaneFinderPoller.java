package com.ruslan.planefinder.client;


import com.ruslan.planefinder.entity.Aircraft;
import com.ruslan.planefinder.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PlaneFinderPoller {

    private final AircraftRepository aircraftRepository;
    private WebClient webClient = WebClient.create("http://localhost:4321/aircrafts");

    @Scheduled(fixedRate = 1000)
    public void poll() {
        aircraftRepository.deleteAll();

        webClient.get()
            .retrieve()
            .bodyToFlux(Aircraft.class)
            .filter(aircraft -> !aircraft.getReg().isEmpty())
            .toStream()
            .forEach(aircraftRepository::save);

        aircraftRepository.findAll().forEach(System.out::println);
    }
}
