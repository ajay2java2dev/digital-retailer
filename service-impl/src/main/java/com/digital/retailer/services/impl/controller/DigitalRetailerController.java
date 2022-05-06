package com.digital.retailer.services.impl.controller;

import com.digital.retailer.services.impl.manager.RetailerServiceManager;
import com.digital.retailer.services.openapi.api.CustomerApi;
import com.digital.retailer.services.openapi.model.CustomerRewardPoints;
import com.digital.retailer.services.openapi.model.RewardPointsQueryParamsSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class DigitalRetailerController implements CustomerApi {

    private RetailerServiceManager retailerServiceManager;

    @Autowired
    public void setRetailerServiceManager(RetailerServiceManager retailerServiceManager) {
        this.retailerServiceManager = retailerServiceManager;
    }

    @Override
    public Mono<ResponseEntity<CustomerRewardPoints>> getCustomerRewardPoints(String xApplicationId, Long customerId,
                                                                              RewardPointsQueryParamsSchema rewardPointsQueryParams,
                                                                              ServerWebExchange exchange) throws Exception {

        //NOTE: Simply checking if customer actually exists before calculating reward points. Not really required.
        return retailerServiceManager.retrieveCustomerDetails(customerId)
                .flatMap(customerEntity ->  retailerServiceManager.retriveCustomerRewardPoints(customerId, rewardPointsQueryParams)
                        .map(customerRewardPoints ->  ResponseEntity.status(HttpStatus.OK).body(customerRewardPoints))
                        .defaultIfEmpty(ResponseEntity.noContent().build())
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
