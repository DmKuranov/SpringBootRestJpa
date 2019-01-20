package ru.dmkuranov.springbootrestjpa.service;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountTransferRequestDto;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferTest extends AbstractTest {
    private static final long targetAccountNo = 2000L;

    @Test
    public void simpleTransfer() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.deposit(AccountTransferRequestDto.of(accountNo, new BigDecimal("100")));
        List<AccountDto> affected = accountService.transfer(AccountTransferRequestDto.of(accountNo, new BigDecimal("20"), targetAccountNo));
        AccountDto source = accountService.get(affected.get(0).getNumber());
        Assert.assertThat(new BigDecimal("80"), Matchers.comparesEqualTo(source.getAmount()));
        AccountDto target = accountService.get(affected.get(1).getNumber());
        Assert.assertThat(new BigDecimal("20"), Matchers.comparesEqualTo(target.getAmount()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void nonPositiveAccountNoTransfer() {
        accountService.transfer(AccountTransferRequestDto.of(0l, new BigDecimal("1"), targetAccountNo));
    }

    @Test(expected = ConstraintViolationException.class)
    public void negativeTargetAccountNoTransfer() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.transfer(AccountTransferRequestDto.of(accountNo, new BigDecimal("1"), 0L));
    }

    @Test(expected = ConstraintViolationException.class)
    public void zeroAmountTransfer() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.transfer(AccountTransferRequestDto.of(accountNo, new BigDecimal("0"), targetAccountNo));
    }

    @Test(expected = ConstraintViolationException.class)
    public void negativeAmountTransfer() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.transfer(AccountTransferRequestDto.of(accountNo, new BigDecimal("-100"), targetAccountNo));
    }

    @Test(expected = ConstraintViolationException.class)
    public void tooLowAmountTransfer() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.transfer(AccountTransferRequestDto.of(accountNo, new BigDecimal("0.009"), targetAccountNo));
    }

    @Test(expected = ConstraintViolationException.class)
    public void tooHighPrecisionAmountTransfer() {
        final long accountNo = accountNoSequence.incrementAndGet();
        accountService.transfer(AccountTransferRequestDto.of(accountNo, new BigDecimal("0.011"), targetAccountNo));
    }
}
