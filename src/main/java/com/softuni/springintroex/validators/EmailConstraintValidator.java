package com.softuni.springintroex.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<ExtendedEmailValidator, String> {

    @Override
    public void initialize(ExtendedEmailValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
