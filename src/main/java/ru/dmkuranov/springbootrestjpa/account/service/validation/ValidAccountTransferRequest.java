package ru.dmkuranov.springbootrestjpa.account.service.validation;

import ru.dmkuranov.springbootrestjpa.account.dto.AccountTransferRequestDto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountTransferRequestValidator.class)
public @interface ValidAccountTransferRequest {
    String message() default "Invalid account transfer request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
