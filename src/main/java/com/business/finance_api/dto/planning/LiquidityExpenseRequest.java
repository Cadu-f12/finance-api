package com.business.finance_api.dto.planning;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record LiquidityExpenseRequest(
        @NotBlank
        String name,

        @PositiveOrZero
        BigDecimal amount
) {
    @Override
    public String toString() {
        return "LiquidityExpenseRequest{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
