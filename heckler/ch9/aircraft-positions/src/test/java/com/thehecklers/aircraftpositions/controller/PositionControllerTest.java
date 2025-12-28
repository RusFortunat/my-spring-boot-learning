package com.thehecklers.aircraftpositions.controller;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import com.thehecklers.aircraftpositions.repository.AircraftRepository;
import com.thehecklers.aircraftpositions.service.PositionRetrieverService;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest
class PositionControllerTest {

    @MockitoBean
    private PositionRetrieverService positionRetrieverService;
    @MockitoBean
    private AircraftRepository aircraftRepository;

    private Aircraft aircraft1, aircraft2;

    @BeforeEach
    void setUp() {
        aircraft1 = new Aircraft();
        aircraft2 = new Aircraft();

        Mockito.when(
                positionRetrieverService.retrieveAircraftPositions())
            .thenReturn(List.of(aircraft1, aircraft2));

        Mockito.when(aircraftRepository.findAll())
            .thenReturn(List.of(aircraft1, aircraft2));
    }


    @Test
    void getAircraftPositions(@Autowired WebTestClient webTestClient) {
        final Iterable acPositions = webTestClient.get()
            .uri("/aircraft")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Iterable.class)
            .returnResult()
            .getResponseBody();

        assertThat(acPositions).isEqualTo(List.of(aircraft1, aircraft2));

        assertEquals(aircraftRepository.findAll(), List.of(aircraft1, aircraft2));
    }

}