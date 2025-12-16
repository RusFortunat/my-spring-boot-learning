package com.ruslan.planefinder.client;

import com.ruslan.planefinder.entity.Aircraft;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
@ConditionalOnProperty(
    name = "redis.component.enabled",
    havingValue = "true",
    matchIfMissing = false
)
public class PlaneFinderPollerRedisImpl {

    private WebClient webClient = WebClient.create("http://localhost:7634/aircraft");

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisOperations<String, Aircraft> redisOperations;

    PlaneFinderPollerRedisImpl(RedisConnectionFactory redisConnectionFactory,
                               RedisOperations<String, Aircraft> redisOperations) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisOperations = redisOperations;
    }

    @Scheduled(fixedRate=1000) // in ms
    private void pollPlanes(){
        redisConnectionFactory.getConnection().serverCommands().flushDb(); // remove all possibly remaining keys

        webClient.get()
            .retrieve()
            .bodyToFlux(Aircraft.class)
            .filter(plane -> !plane.getReg().isEmpty())
            .toStream()
            .forEach(plane ->
                redisOperations.opsForValue().set(plane.getReg(), plane));

        redisOperations.opsForValue()
            .getOperations()
            .keys("*")
            .forEach(plane ->
                System.out.println(redisOperations.opsForValue().get(plane)));
    }
}
