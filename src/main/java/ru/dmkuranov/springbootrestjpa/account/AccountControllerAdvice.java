package ru.dmkuranov.springbootrestjpa.account;

import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.dmkuranov.springbootrestjpa.account.service.AccountService.AccountNotFoundException;
import ru.dmkuranov.springbootrestjpa.account.service.AccountService.InsufficientFundsException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AccountControllerAdvice {

    @ResponseBody
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String accountNotFoundHandler(AccountNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String insufficientFundsHandler(InsufficientFundsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String validationErrorHandler(ConstraintViolationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(StaleObjectStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String optimisticLockFailureHandler(StaleObjectStateException ex) {
        return ex.getMessage();
    }
}
