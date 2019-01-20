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

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepositTest extends AbstractTest {

    @Test
    public void simpleDeposit() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("100")));
        AccountDto stored = accountService.get(accountNo);
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(stored.getAmount()));
    }

    @Test
    public void sequenceDeposit() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("100")));
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("1")));
        AccountDto stored = accountService.get(accountNo);
        Assert.assertThat(new BigDecimal("101"), Matchers.comparesEqualTo(stored.getAmount()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void nonPositiveAccountNoDeposit() {
        accountService.deposit(AccountChangeRequestDto.of(0l, new BigDecimal("1")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void zeroAmountDeposit() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("0")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void negativeAmountDeposit() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("-100")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void tooLowAmountDeposit() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("0.009")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void tooHighPrecisionAmountDeposit() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountChangeRequestDto.of(accountNo, new BigDecimal("0.011")));
    }
}
