package ru.dmkuranov.springbootrestjpa.account.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountChangeRequestDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountTransferRequestDto;
import ru.dmkuranov.springbootrestjpa.account.service.validation.ValidAccountChangeRequest;
import ru.dmkuranov.springbootrestjpa.account.service.validation.ValidAccountTransferRequest;

import java.util.List;

@Validated
public interface AccountService {
    @NotNull AccountDto get(@NotNull Long accountNumber);

    @NotNull AccountDto deposit(@NotNull @ValidAccountChangeRequest AccountChangeRequestDto changeRequest);

    @NotNull AccountDto withdraw(@NotNull @ValidAccountChangeRequest AccountChangeRequestDto changeRequest);

    /**
     * @param transferRequestDto
     * @return Состояние счетов источника и приемника в списке
     */
    @NotNull List<AccountDto> transfer(@NotNull @ValidAccountTransferRequest AccountTransferRequestDto transferRequestDto);

    class AccountNotFoundException extends RuntimeException {
        public AccountNotFoundException(String message) {
            super(message);
        }
    }

    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}
