package ru.dmkuranov.springbootrestjpa.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountTransferRequestDto extends AccountChangeRequestDto {
    private final Long targetNumber;

    protected AccountTransferRequestDto(Long number, BigDecimal amount, Long targetNumber) {
        super(number, amount);
        this.targetNumber = targetNumber;
    }

    public static AccountTransferRequestDto of(Long number, BigDecimal amount, Long targetNumber) {
        return new AccountTransferRequestDto(number, amount, targetNumber);
    }
}
