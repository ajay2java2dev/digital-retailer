package com.digital.retailer.services.impl.manager;

import com.digital.retailer.services.data.model.CustomerEntity;
import com.digital.retailer.services.data.model.CustomerRewardsEntity;
import com.digital.retailer.services.data.repositories.CustomerRepository;
import com.digital.retailer.services.data.repositories.CustomerRewardsRepository;
import com.digital.retailer.services.data.repositories.PaymentTransactionsRepository;
import com.digital.retailer.services.impl.exception.DigitalRestClientException;
import com.digital.retailer.services.impl.service.PointsService;
import com.digital.retailer.services.openapi.model.CustomerRewardPoints;
import com.digital.retailer.services.openapi.model.Error;
import com.digital.retailer.services.openapi.model.Errors;
import com.digital.retailer.services.openapi.model.RewardPointsQueryParamsSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class RetailerServiceManager {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

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

    public Mono<CustomerRewardPoints> retrieveCustomerRewardPoints (Long customerId, RewardPointsQueryParamsSchema rewardPointsQueryParams){

        //Default 3 months. Also set at contract level, so not really required here.
        LocalDateTime previousDate = LocalDateTime.now().minusMonths(rewardPointsQueryParams != null
                && rewardPointsQueryParams.getNumOfMonths()!=null?rewardPointsQueryParams.getNumOfMonths():3);

        var custTransAmountFlux = Mono.just(paymentTransactionsRepository
                        .findAllByCustomerIdAndTransDateTimeGreaterThanEqual(customerId, previousDate))
                .flatMapIterable(list -> list)
                .map(paymentTransactionsEntity -> paymentTransactionsEntity.getAmount())
                .doOnError(throwable -> LOG.error("An exception has occured: {}", throwable.getMessage(), throwable));

        //calculate points, save if valid and return the points to client request.
        return pointsService.calculatePoints(custTransAmountFlux)
                .flatMap(points -> {
                    try {
                        var customerRewardsEntity = new CustomerRewardsEntity();
                        customerRewardsEntity.setCustomerId(customerId);
                        customerRewardsEntity.setRewardPoints(points);
                        customerRewardsEntity.setLastUpdatedDttm(LocalDateTime.now());
                        customerRewardsRepository.saveAndFlush(customerRewardsEntity);
                    }catch (Exception ex) {
                        LOG.error("An db exception has occurred: {}", ex.getMessage(), ex);
                        //NOTE: I purposefully skipped throwing this exception as it's not mandatory to save the customer Rewards as of now.
                    }
                    return Mono.just(points);
                })
                .flatMap(points -> Mono.just(new CustomerRewardPoints().customerId(customerId).rewardPoints(points)))
                .onErrorResume(throwable ->
                        //wrap any exceptions into custom exception
                        Mono.error(new DigitalRestClientException(throwable.getLocalizedMessage(),
                                new Errors().addErrorsItem(new Error()
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message(throwable.getLocalizedMessage()))
                                , HttpStatus.INTERNAL_SERVER_ERROR))
                );
    }
}
