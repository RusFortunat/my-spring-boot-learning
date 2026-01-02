package com.thehecklers.aircraftpositions;

import com.thehecklers.aircraftpositions.controller.PositionController;
import com.thehecklers.aircraftpositions.entity.Aircraft;
import com.thehecklers.aircraftpositions.repository.AircraftRepository;
import com.thehecklers.aircraftpositions.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(controllers = {PositionController.class})
class PositionControllerTest {
    @MockitoBean
    private PositionService positionService;
    @MockitoBean
    private RSocketRequester rSocketRequester;
    @Autowired
    private WebTestClient client;

    private Aircraft ac1, ac2;

    @BeforeEach
    void setUp(ApplicationContext context) {
        // Spring Airlines flight 001 en route, flying STL to SFO,
        //    at 30000' currently over Kansas City
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL,
        //    at 40000' currently over Denver
        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        //Hooks.onOperatorDebug();
        ReactorDebugAgent.init();
        Mockito.when(positionService.getCurrentAircraftPositions())
            .thenReturn(Flux.just(ac1, ac2)
//                .checkpoint("Lightweight checkpoint before")
//                .concatWith(Flux.error(new Throwable("Bad position report")))
//                .checkpoint("Lightweight checkpoint after")
            );
        Mockito.when(positionService.getACPositionById(1L))
            .thenReturn(Mono.just(ac1));
        Mockito.when(positionService.getACPositionById(2L))
            .thenReturn(Mono.just(ac2));
        Mockito.when(positionService.getACPositionByRegistration("N54321"))
            .thenReturn(Flux.just(ac2));
    }

//    @Test   // NOTE: This test fails prior to refactoring
//    void getCurrentAircraftPositions() {
//        final Iterable<Aircraft> acPositions = client.get()
//                .uri("/aircraft")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Aircraft.class)
//                .returnResult()
//                .getResponseBody();
//
//        assertEquals(List.of(ac1, ac2), acPositions);
//    }

    @Test
    void getCurrentACPositions() {
        StepVerifier.create(client.get()
                .uri("/acpos")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody()
            )
            .expectNext(ac1)
            .expectNext(ac2)
            .verifyComplete();
    }

    @Test
    void searchForACPositionByIdTest(){
        StepVerifier.create(client.get()
                .uri("/acpos/search?id=2")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody()
            )
            .expectNext(ac2)
            .verifyComplete();
    }

    @Test
    void searchForACPositionByRegTest(){
        StepVerifier.create(client.get()
                .uri("/acpos/search?reg=N54321")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody()
            )
            .expectNext(ac2)
            .verifyComplete();
    }
}
