package ru.dmkuranov.springbootrestjpa.account.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountChangeRequestDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountTransferRequestDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    @NotNull
    public AccountDto get(final Long accountNumber) {
        return accountMapper.toDto(retrieveAccount(accountNumber));
    }

    @Override
    @Transactional
    @NotNull
    public AccountDto deposit(AccountChangeRequestDto changeRequest) {
        Long accountNumber = changeRequest.getNumber();
        BigDecimal amount = changeRequest.getAmount();
        Account account = accountRepository.findById(accountNumber).orElse(null);
        if (account != null) {
            account.setAmount(account.getAmount().add(amount));
            account = accountRepository.save(account);
            log.info("Deposit {} to account #{}, amount {}", amount, accountNumber, account.getAmount());
        } else {
            account = accountRepository.save(
                    Account.builder().number(accountNumber).amount(amount).build()
            );
            log.info("Opened account #{}, amount {}", accountNumber, account.getAmount());
        }
        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountDto withdraw(AccountChangeRequestDto changeRequest) {
        BigDecimal withdrawAmount = changeRequest.getAmount();
        Account account = retrieveAccount(changeRequest.getNumber());
        BigDecimal accountAmount = account.getAmount();
        if (accountAmount.compareTo(withdrawAmount) >= 0) {
            account.setAmount(accountAmount.subtract(withdrawAmount));
            account = accountRepository.save(account);
            return accountMapper.toDto(account);
        }
        throw new InsufficientFundsException("Insufficient Funds on account #" + changeRequest.getNumber());
    }

    @Override
    @Transactional
    public List<AccountDto> transfer(
            final AccountTransferRequestDto transferRequestDto) {
        AccountDto sourceAccount = withdraw(transferRequestDto);
        AccountDto targetAccount = deposit(AccountChangeRequestDto.of(
                transferRequestDto.getTargetNumber(), transferRequestDto.getAmount()));
        return Arrays.asList(sourceAccount, targetAccount);
    }

    protected Account retrieveAccount(final Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountServiceImpl.AccountNotFoundException("Account #" + accountNumber + " not found"));
    }
}
