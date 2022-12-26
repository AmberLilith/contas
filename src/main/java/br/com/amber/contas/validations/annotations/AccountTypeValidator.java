package br.com.amber.contas.validations.annotations;

import br.com.amber.contas.enums.AccountType;
import br.com.amber.contas.enums.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountTypeValidator implements ConstraintValidator<IsValidAccountType, String> {
    @Override
    public void initialize(IsValidAccountType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for(AccountType type : AccountType.values()){
            if(type.name().equals(value)){
                return true;
            }
        }
        return false;
    }
}
