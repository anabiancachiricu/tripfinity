package com.unibuc.tripfinity.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnlyNumbersValidation implements ConstraintValidator<OnlyNumbers, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null)
            return false;
        return value.matches("^[0-9]*$");
    }
}
