package com.example.demoAOP.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ContainerOfObjects {

    private final static int CONTAINER_SIZE = 100;

    private final int[] containerOfIntegers;

    public ContainerOfObjects() {
        // populate array with random integers
        Random random = new Random();
        containerOfIntegers = new int[CONTAINER_SIZE];
        for (int i = 0; i < CONTAINER_SIZE; i++) {
            containerOfIntegers[i] = random.nextInt(100);
        }
    }

    public int getRandomIntElement() {
        return containerOfIntegers[new Random().nextInt(containerOfIntegers.length)];
    }
}
