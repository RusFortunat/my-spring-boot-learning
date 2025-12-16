package com.ruslan.planefinder.client;

import com.ruslan.planefinder.entity.Aircraft;
import com.ruslan.planefinder.repository.AircraftRepository;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@EnableScheduling
public class PlaneFinderPoller {

    private WebClient webClient = WebClient.create("http://localhost:4321/aircrafts");

    private final RedisConnectionFactory redisConnectionFactory;
    private final AircraftRepository aircraftRepository;

    PlaneFinderPoller(RedisConnectionFactory redisConnectionFactory,
                                AircraftRepository aircraftRepository) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.aircraftRepository = aircraftRepository;
    }

    @Scheduled(fixedRate = 1000)
    public void pollPlanes(){
        redisConnectionFactory.getConnection().serverCommands().flushDb();

        webClient.get()
            .retrieve()
            .bodyToFlux(Aircraft.class)
            .filter(plane -> !plane.getReg().isEmpty())
            .toStream()
            .forEach(aircraftRepository::save);

        aircraftRepository.findAll().forEach(System.out::println);
    }
}
