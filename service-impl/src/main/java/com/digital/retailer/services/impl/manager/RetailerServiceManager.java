package com.digital.retailer.services.impl.manager;

import com.digital.retailer.services.data.model.CustomerEntity;
import com.digital.retailer.services.data.repositories.CustomerRepository;
import com.digital.retailer.services.data.repositories.CustomerRewardsRepository;
import com.digital.retailer.services.data.repositories.PaymentTransactionsRepository;
import com.digital.retailer.services.impl.service.PointsService;
import com.digital.retailer.services.openapi.model.CustomerRewardPoints;
import com.digital.retailer.services.openapi.model.RewardPointsQueryParamsSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class RetailerServiceManager {

    private static final Logger LOG = LoggerFactory.getLogger(RetailerServiceManager.class);

    private PointsService pointsService;
    private CustomerRepository customerRepository;
    private PaymentTransactionsRepository paymentTransactionsRepository;
    private CustomerRewardsRepository customerRewardsRepository;


    @Autowired
    public void setPointsService(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @Autowired
    public void setPaymentTransactionsRepository(PaymentTransactionsRepository paymentTransactionsRepository) {
        this.paymentTransactionsRepository = paymentTransactionsRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setCustomerRewardsRepository(CustomerRewardsRepository customerRewardsRepository) {
        this.customerRewardsRepository = customerRewardsRepository;
    }

    public Mono<CustomerEntity> retrieveCustomerDetails (Long customerId){
        return Mono.justOrEmpty(customerRepository.findById(customerId));
    }

    public Mono<CustomerRewardPoints> retriveCustomerRewardPoints (Long customerId, RewardPointsQueryParamsSchema rewardPointsQueryParams){

        //Default 3 months
        LocalDateTime previousDate = LocalDateTime.now()
                .minusMonths(rewardPointsQueryParams.getNumOfMonths()!=null?rewardPointsQueryParams.getNumOfMonths():3);

        var custTransAmountFlux = Mono.just(paymentTransactionsRepository
                        .findAllByCustomerIdAndTransDateTimeGreaterThanEqual(customerId, previousDate))
                .flatMapIterable(list -> list)
                .map(paymentTransactionsEntity -> paymentTransactionsEntity.getAmount())
                .doOnError(throwable -> LOG.error("An exception has occured: {}", throwable.getMessage(), throwable));

        //calculate points and return
        return pointsService.calculatePoints(custTransAmountFlux)
                .flatMap(points -> Mono.just(new CustomerRewardPoints().customerId(customerId).rewardPoints(points)));
    }
}
