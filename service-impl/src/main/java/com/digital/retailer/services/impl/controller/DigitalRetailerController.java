package com.digital.retailer.services.impl.controller;

import com.digital.retailer.services.impl.manager.RetailerServiceManager;
import com.digital.retailer.services.impl.validator.RewardPointsQueryParamsSchemaValidator;
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
    private RewardPointsQueryParamsSchemaValidator rewardPointsQueryParamsSchemaValidator;

    @Autowired
    public void setRetailerServiceManager(RetailerServiceManager retailerServiceManager) {
        this.retailerServiceManager = retailerServiceManager;
    }

    @Autowired
    public void setRewardPointsQueryParamsSchema(RewardPointsQueryParamsSchemaValidator rewardPointsQueryParamsSchemaValidator) {
        this.rewardPointsQueryParamsSchemaValidator = rewardPointsQueryParamsSchemaValidator;
    }

    @Override
    public Mono<ResponseEntity<CustomerRewardPoints>> getCustomerRewardPoints(String xApplicationId, Long customerId,
                                                                              RewardPointsQueryParamsSchema rewardPointsQueryParams,
                                                                              ServerWebExchange exchange) throws Exception {

        //NOTE: I am simply doing a spring validation here. This can be done at API contract level itself
        //NOTE: Also retrieveCustomerDetails is not really required since the FK contraints are already present in the Schema.sql
        return rewardPointsQueryParamsSchemaValidator.validateRewardPointsQueryParams(rewardPointsQueryParams)
                .then(retailerServiceManager.retrieveCustomerDetails(customerId)
                        .flatMap(customerEntity ->  retailerServiceManager.retrieveCustomerRewardPoints(customerId, rewardPointsQueryParams)
                                .map(customerRewardPoints ->  ResponseEntity.status(HttpStatus.OK).body(customerRewardPoints))
                                .defaultIfEmpty(ResponseEntity.noContent().build())
                        ).defaultIfEmpty(ResponseEntity.notFound().build()));
    }
}
