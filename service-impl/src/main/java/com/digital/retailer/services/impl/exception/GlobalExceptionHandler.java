package com.digital.retailer.services.impl.exception;

import com.digital.retailer.services.impl.controller.DigitalRetailerController;
import com.digital.retailer.services.openapi.api.CustomerApi;
import com.digital.retailer.services.openapi.model.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice(basePackageClasses = {CustomerApi.class, DigitalRetailerController.class})
public class GlobalExceptionHandler {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({DigitalRestClientException.class})
    protected Mono<ResponseEntity<Errors>> handleDigitalRestClientExcepion(DigitalRestClientException ex) {
        LOG.error("An exception has occurred: {}", ex.getMessage(), ex);
        return Mono.just(ex.getMessage()).map(res -> ResponseEntity.status(ex.getStatus()).body(ex.getErrors()));
    }
}
