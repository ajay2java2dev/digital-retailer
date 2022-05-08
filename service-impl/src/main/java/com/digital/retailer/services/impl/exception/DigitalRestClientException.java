package com.digital.retailer.services.impl.exception;

import com.digital.retailer.services.openapi.model.Errors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DigitalRestClientException extends RuntimeException {

    private Errors errors;
    private HttpStatus status;

    public DigitalRestClientException(String message, Errors errors, HttpStatus status) {
        super(message);
        this.errors = errors;
        this.status = status;
    }
}
