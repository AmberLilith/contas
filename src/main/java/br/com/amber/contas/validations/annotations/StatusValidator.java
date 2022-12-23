package br.com.amber.contas.validations.annotations;

import br.com.amber.contas.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<IsValidStatus, String> {
    @Override
    public void initialize(IsValidStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for(Status status : Status.values()){
            if(status.name().equals(value)){
                return true;
            }
        }
        return false;
    }
}
