package com.digital.retailer.service.impl;

import com.digital.retailer.service.impl.service.PointsServiceManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class ServiceImplApplicationTests {

    @Autowired
    PointsServiceManager pointsServiceManager;

    @Test
    void whenValidTransaction_ThenReturnPointsGiven() {
        var transactions = Flux.just (10.00, 75.00, 101.00, 102.00, 103.00);
        StepVerifier.create(pointsServiceManager.calculatePoints(transactions))
                .expectNext(187l) //total sum (75-50) + (50 + (101-101)) + (50 + (101-102)) + (50 + (101-103))
                .expectNextCount(0) //total sum should be return above and after that nothing should be expected
                .verifyComplete();
    }

    @Test
    void whenInValidTransaction_ThenReturnPointsGiven() {
        var transactions = Flux.just (10.00, 75.00, 101.00, 102.00, 103.00, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        StepVerifier.create(pointsServiceManager.calculatePoints(transactions))
                .expectNext(187l) //total sum (75-50) + (50 + (101-101)) + (50 + (101-102)) + (50 + (101-103))
                .expectNextCount(0) //total sum should be return above and after that nothing should be expected
                .verifyComplete();
    }

}
