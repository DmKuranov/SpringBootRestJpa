package ru.dmkuranov.springbootrestjpa.account.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountChangeRequestValidator.class)
public @interface ValidAccountChangeRequest {
    String message() default "Invalid account change request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
