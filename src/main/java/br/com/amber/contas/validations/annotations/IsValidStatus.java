package br.com.amber.contas.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
@Documented
public @interface IsValidStatus {

    String message() default "Precisa ser um status válido: [ATIVO, INATIVO]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
