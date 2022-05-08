package com.digital.retailer.services.impl.service;

import com.digital.retailer.services.impl.config.H2ConsoleConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PointsServiceTest {

    @Autowired
    PointsService pointsServiceManager;

    @Autowired
    H2ConsoleConfig h2ConsoleConfig;
    @AfterAll
    void destory(){
        h2ConsoleConfig.webServer.stop();
    }

    @Test
    void whenValidTransaction_ThenReturnPointsGiven() {
        var transactions = Flux.just (10.00, 75.00, 101.00, 102.00, 103.00);
        StepVerifier.create(pointsServiceManager.calculatePoints(transactions))
                .expectNext(187l) //total sum (75-50) + (50 + (101-101)) + (50 + (101-102)) + (50 + (101-103))
                .expectNextCount(0) //total sum should be return above and after that nothing should be expected
                .verifyComplete();
    }

    @Test
    void whenInValidTransactionInList_ThenReturnValidPointsGiven() {
        var transactions = Flux.just (10.00, 75.00, 101.00, 102.00, 103.00, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        StepVerifier.create(pointsServiceManager.calculatePoints(transactions))
                .expectNext(187l) //total sum (75-50) + (50 + (101-101)) + (50 + (101-102)) + (50 + (101-103))
                .expectNextCount(0) //total sum should be return above and after that nothing should be expected
                .verifyComplete();
    }

    @Test
    void whenEmptyTransaction_ThenReturnZeroPointsGiven() {
        StepVerifier.create(pointsServiceManager.calculatePoints(Flux.empty()))
                .expectNext(0l) //total sum (75-50) + (50 + (101-101)) + (50 + (101-102)) + (50 + (101-103))
                .expectNextCount(0) //total sum should be return above and after that nothing should be expected
                .verifyComplete();
    }

    @Test
    void whenInvalidTransaction_ThenValidateException() {

        //Note: 999 is not an invalid input, but I am using it here to mock an error scenario
        Function<Double, Double> mapper = input -> {
            if (input.equals(999.00)) {
                throw new NumberFormatException("Invalid number being used");
            }
            return input;
        };

        //Note: Point service instead of throwing the exception and exiting, continues to calculate with Valid values and still returns.
        StepVerifier.create(pointsServiceManager
                        .calculatePoints(Flux.just (10.00, 75.00, 101.00, 102.00, 103.00, 999.00).map(mapper)))
                .expectNext(187l)
                .expectNextCount(0)
                .verifyComplete();
    }
}
