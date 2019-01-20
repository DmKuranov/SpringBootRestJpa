package ru.dmkuranov.springbootrestjpa.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmountAndTargetAccountDto {
    private BigDecimal amount;
    private Long targetAccountNumber;

    public static AmountAndTargetAccountDto of(String amountValue, Long targetAccountNumber) {
        return builder().amount(new BigDecimal(amountValue)).targetAccountNumber(targetAccountNumber).build();
    }
}
