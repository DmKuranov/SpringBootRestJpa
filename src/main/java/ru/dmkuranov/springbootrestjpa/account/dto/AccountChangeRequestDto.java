package ru.dmkuranov.springbootrestjpa.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountChangeRequestDto extends ResourceSupport {
    private final Long number;
    private final BigDecimal amount;

    protected AccountChangeRequestDto(Long number, BigDecimal amount) {
        this.number = number;
        this.amount = amount;
    }

    public static AccountChangeRequestDto of(Long number, BigDecimal amount) {
        return new AccountChangeRequestDto(number, amount);
    }
}
