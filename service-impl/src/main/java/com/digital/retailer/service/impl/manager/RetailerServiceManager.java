package com.digital.retailer.service.impl.manager;

import com.digital.retailer.service.impl.service.PointsService;
import com.digital.retailer.services.model.CustomerRewardPoints;
import com.digital.retailer.services.model.RewardPointsQueryParamsSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class RetailerServiceManager {

    private PointsService pointsService;

    @Autowired
    public void setPointsService(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    public Mono<CustomerRewardPoints> retriveCustomerRewardPoints (Long customerId,
                                                                   RewardPointsQueryParamsSchema rewardPointsQueryParams){
        //TODO: Retrieve from DB customer records
        return pointsService.calculatePoints(Flux.just (10.00, 75.00, 101.00, 102.00, 103.00))
                .flatMap(points -> Mono.just(new CustomerRewardPoints().customerId(customerId).rewardPoints(points)));
    }
}
