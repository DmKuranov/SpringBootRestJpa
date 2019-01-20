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
public class AmountDto {
    private BigDecimal amount;

    public static AmountDto of(String amountValue) {
        return builder().amount(new BigDecimal(amountValue)).build();
    }
}
