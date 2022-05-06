package com.digital.retailer.services.impl.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DigitalRetailerRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
