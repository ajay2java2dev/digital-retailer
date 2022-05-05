package com.digital.retailer.service.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PointsServiceManager {

    private static final Logger LOG = LoggerFactory.getLogger(PointsServiceManager.class);

    public Mono<Long> calculatePoints (Flux<Double> transactions) {
        return transactions
                .filter(transaction -> !transaction.isNaN() && !transaction.isInfinite())
                .flatMap(transaction -> {
                    var pointsGiven = 0l;
                    if (transaction >= 50 && transaction < 100) {
                        pointsGiven += (long)Math.ceil(1 * (transaction-50));
                    }
                    if (transaction >= 50 && transaction >= 100) {
                        pointsGiven += (long)Math.ceil(1 * 50); // get 50 points for 50 - 100
                        pointsGiven += (long)Math.ceil(2 * (transaction.longValue() - 100)); // get double after 100
                    }
                    return Mono.just(pointsGiven);
                })
                .reduce(Long::sum);
    }
}
