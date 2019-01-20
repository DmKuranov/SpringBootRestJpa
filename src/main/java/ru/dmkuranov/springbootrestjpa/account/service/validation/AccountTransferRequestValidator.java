package ru.dmkuranov.springbootrestjpa.account.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountChangeRequestDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountTransferRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class AccountTransferRequestValidator implements ConstraintValidator<ValidAccountTransferRequest, AccountTransferRequestDto> {
    private final AccountChangeRequestValidator accountChangeRequestValidator;

    public AccountTransferRequestValidator() {
        this.accountChangeRequestValidator = new AccountChangeRequestValidator();
    }

    @Override
    public boolean isValid(AccountTransferRequestDto accountTransferRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        if (!accountChangeRequestValidator.isValid(accountTransferRequestDto, constraintValidatorContext)) {
            return false;
        }
        if (accountTransferRequestDto.getTargetNumber() <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ValidAccountTransferRequest constraintAnnotation) {
    }
}
