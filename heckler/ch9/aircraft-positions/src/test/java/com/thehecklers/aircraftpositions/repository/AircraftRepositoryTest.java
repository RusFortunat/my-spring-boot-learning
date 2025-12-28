package com.thehecklers.aircraftpositions.repository;

import com.thehecklers.aircraftpositions.entity.Aircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AircraftRepositoryTest {

    @Autowired
    private AircraftRepository aircraftRepository;
    private Aircraft aircraft1;
    private Aircraft aircraft2;

    @BeforeEach
    void setUp() {
        aircraft1 =  new Aircraft();
        aircraft2 =  new Aircraft();

        aircraftRepository.saveAll(
            List.of(aircraft1, aircraft2)
        );
    }

    @AfterEach
    void tearDown() {
        aircraftRepository.deleteAll();
    }

    @Test
    void findAll() {
        Iterable<Aircraft> aircrafts = aircraftRepository.findAll();

        assertNotNull(aircrafts);
        assertEquals(2, List.of(aircrafts).size());
        assertEquals(List.of(aircraft1, aircraft2), aircrafts);
    }

    @Test
    void findById() {

        Optional<Aircraft> aircraft = aircraftRepository.findById(1L);

        assertTrue(aircraft.isPresent());
        assertEquals(aircraft1.getId(), aircraft.get().getId());
    }
}