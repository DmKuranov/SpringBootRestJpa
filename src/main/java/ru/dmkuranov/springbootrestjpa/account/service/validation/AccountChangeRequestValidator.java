package ru.dmkuranov.springbootrestjpa.account.service.validation;

import ru.dmkuranov.springbootrestjpa.account.dto.AccountChangeRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class AccountChangeRequestValidator implements ConstraintValidator<ValidAccountChangeRequest, AccountChangeRequestDto> {
    @Override
    public boolean isValid(AccountChangeRequestDto accountChangeRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        if (accountChangeRequestDto.getNumber() <= 0) {
            return false;
        }
        BigDecimal amount = accountChangeRequestDto.getAmount();
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            return false;
        }
        try {
            // Точность суммы свыше 2-х знаков недопустима
            amount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        } catch (ArithmeticException e) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ValidAccountChangeRequest constraintAnnotation) {
    }
}
