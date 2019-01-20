package ru.dmkuranov.springbootrestjpa.service;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountChangeRequestDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountDto;
import ru.dmkuranov.springbootrestjpa.account.service.AccountService;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WithdrawTest extends AbstractTest {

    @Test
    public void simpleWithdraw() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("100")));
        accountService.withdraw(AccountChangeRequestDto.of(accountNo, new BigDecimal("30")));
        AccountDto stored = accountService.get(accountNo);
        Assert.assertThat(new BigDecimal("70"), Matchers.comparesEqualTo(stored.getAmount()));
    }

    @Test(expected = AccountService.InsufficientFundsException.class)
    public void insufficientFundsWithdraw() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("100")));
        accountService.withdraw(AccountChangeRequestDto.of(accountNo, new BigDecimal("110")));
    }

    @Test(expected = AccountService.AccountNotFoundException.class)
    public void noAccountWithdraw() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.withdraw(AccountChangeRequestDto.of(accountNo, new BigDecimal("110")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void nonPositiveAccountNoWithdraw() {
        accountService.deposit(AccountChangeRequestDto.of(0l, new BigDecimal("1")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void zeroAmountWithdraw() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("0")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void negativeAmountWithdraw() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("-100")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void tooLowAmountWithdraw() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("0.009")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void tooHighPrecisionAmountWithdraw() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("0.011")));
    }
}
