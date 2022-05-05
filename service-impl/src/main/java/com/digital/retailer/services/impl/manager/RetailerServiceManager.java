package com.digital.retailer.services.impl.manager;

import com.digital.retailer.services.impl.service.PointsService;
import com.digital.retailer.services.data.repositories.PaymentTransactionsRepository;
import com.digital.retailer.services.model.CustomerRewardPoints;
import com.digital.retailer.services.model.RewardPointsQueryParamsSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RetailerServiceManager {

    private PointsService pointsService;
    private PaymentTransactionsRepository paymentTransactionsRepository;

    @Autowired
    public void setPointsService(PointsService pointsService) {
        this.pointsService = pointsService;
    }

//    @Autowired
//    public void setPaymentTransactionsRepository(PaymentTransactionsRepository paymentTransactionsRepository) {
//        this.paymentTransactionsRepository = paymentTransactionsRepository;
//    }

    public Mono<CustomerRewardPoints> retriveCustomerRewardPoints (Long customerId,
                                                                   RewardPointsQueryParamsSchema rewardPointsQueryParams){
        //TODO: Retrieve from DB customer records
        return pointsService.calculatePoints(Flux.just (10.00, 75.00, 101.00, 102.00, 103.00))
                .flatMap(points -> Mono.just(new CustomerRewardPoints().customerId(customerId).rewardPoints(points)));
    }
}
