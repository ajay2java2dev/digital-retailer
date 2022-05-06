package com.digital.retailer.services.impl.validator;

import com.digital.retailer.services.impl.exception.DigitalRestClientException;
import com.digital.retailer.services.openapi.model.Error;
import com.digital.retailer.services.openapi.model.RewardPointsQueryParamsSchema;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

@Component
public class RewardPointsQueryParamsSchemaValidator implements Validator {

    private static final String REWARDS_QUERY_PARAM_LOG_MSG = "Rewards Query Param validation failed";

    @Override
    public boolean supports(Class<?> clazz) {
        return RewardPointsQueryParamsSchema.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //NOTE: This is just an assumption to consider only last 10 years of data
        var rewardPointsQueryParamsSchema = (RewardPointsQueryParamsSchema) target;
        if (rewardPointsQueryParamsSchema.getNumOfMonths() <= 0 || rewardPointsQueryParamsSchema.getNumOfMonths()>120) {
            errors.reject("numOfMonths", "numOfMonths should be between 1 & 120");
        }
    }

    public Mono<RewardPointsQueryParamsSchema> validateRewardPointsQueryParams (RewardPointsQueryParamsSchema rewardPointsQueryParamsSchema) {
        var beanPropertyBindingResult = new BeanPropertyBindingResult(rewardPointsQueryParamsSchema, RewardPointsQueryParamsSchema.class.getName());
        this.validate(rewardPointsQueryParamsSchema, beanPropertyBindingResult);
        if (beanPropertyBindingResult.hasErrors()) {
            var errorsSchema = new com.digital.retailer.services.openapi.model.Errors();
            beanPropertyBindingResult.getAllErrors().forEach(objectError -> {
                Error errorSchema = new Error().status(HttpStatus.BAD_REQUEST.getReasonPhrase() + ": " + objectError.getCode())
                        .message(objectError.getDefaultMessage());
                errorsSchema.addErrorsItem(errorSchema);
            });
            return Mono.error(new DigitalRestClientException(REWARDS_QUERY_PARAM_LOG_MSG, errorsSchema, HttpStatus.BAD_REQUEST));
        }
        return Mono.just(rewardPointsQueryParamsSchema);
    }
}
