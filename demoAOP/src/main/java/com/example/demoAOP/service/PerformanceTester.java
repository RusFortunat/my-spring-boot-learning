package com.example.demoAOP.service;

import com.example.demoAOP.aspect.PerformanceAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PerformanceTester {

    Logger log = LoggerFactory.getLogger(PerformanceTester.class);

    private final ContainerOfObjects containerOfIntegers;
    private final PerformanceAspect performanceAspect;

    public PerformanceTester(ContainerOfObjects containerOfIntegers,
                             PerformanceAspect performanceAspect) {
        this.containerOfIntegers = containerOfIntegers;
        this.performanceAspect = performanceAspect;
    }

    @Scheduled(initialDelay = 1000)
    public void testSingleInvocation() {
        log.info("Test single invocation from array of integers");
        containerOfIntegers.getRandomIntElement();
    }

    @Scheduled(initialDelay = 5000)
    public void testMultipleInvocation() {

        log.info("Test multiple invocation from array of integers");

        final int numberOfInvocations = 10;
        long timeSum = 0L;
        for (int i = 0; i < numberOfInvocations; i++) {
            containerOfIntegers.getRandomIntElement();
            timeSum += performanceAspect.getLastExecutionTime();
        }

        log.info("Average invotation time after {} iterations: {} nanoseconds",
            numberOfInvocations,
            timeSum / numberOfInvocations);
    }
}
