package ru.dmkuranov.springbootrestjpa.account;

import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dmkuranov.springbootrestjpa.account.dto.*;
import ru.dmkuranov.springbootrestjpa.account.service.AccountMapper;
import ru.dmkuranov.springbootrestjpa.account.service.AccountService;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping(value = "/accounts/{accountNumber}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<AccountDto> get(@PathVariable Long accountNumber) {
        AccountDto account = accountService.get(accountNumber);
        return accountMapper.toResource(account);
    }

    @PostMapping(value = "/accounts/{accountNumber}/deposit", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<AccountDto> deposit(@PathVariable Long accountNumber, @RequestBody AmountDto amountDto) {
        AccountChangeRequestDto changeRequestDto = AccountChangeRequestDto.of(accountNumber, amountDto.getAmount());
        AccountDto account = accountService.deposit(changeRequestDto);
        return accountMapper.toResource(account);
    }

    @PostMapping(value = "/accounts/{accountNumber}/withdraw", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<AccountDto> withdraw(@PathVariable Long accountNumber, @RequestBody AmountDto accountDeposit) {
        AccountChangeRequestDto changeRequestDto = AccountChangeRequestDto.of(accountNumber, accountDeposit.getAmount());
        AccountDto account = accountService.withdraw(changeRequestDto);
        return accountMapper.toResource(account);
    }

    @PostMapping(value = "/accounts/{accountNumber}/transfer", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<AccountDto> transfer(@PathVariable Long accountNumber, @RequestBody AmountAndTargetAccountDto amountAndAccount) {
        AccountTransferRequestDto changeRequestDto = AccountTransferRequestDto.of(accountNumber
                , amountAndAccount.getAmount(), amountAndAccount.getTargetAccountNumber());
        List<AccountDto> accounts = accountService.transfer(changeRequestDto);
        return accountMapper.toResource(accounts.get(0));
    }
}
