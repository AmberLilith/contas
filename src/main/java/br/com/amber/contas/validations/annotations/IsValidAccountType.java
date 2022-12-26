package br.com.amber.contas.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountTypeValidator.class)
@Documented
public @interface IsValidAccountType {

    String message() default "Precisa ser um tipo de conta válido: [CORRENTE, POUPANCA, SALARIO]" + 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
