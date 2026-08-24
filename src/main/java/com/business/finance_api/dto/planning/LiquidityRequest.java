package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record LiquidityRequest(
        @NotNull
        @JsonProperty("reference_date")
        LocalDate referenceDate,

        @PositiveOrZero
        BigDecimal salary,

        @PositiveOrZero
        @JsonProperty("current_balance")
        BigDecimal currentBalance,

        @NotEmpty
        List<@Valid LiquidityExpenseRequest> expenses
) {

    @Override
    public String toString() {
        return "LiquidityRequest{" +
                "referenceDate=" + referenceDate +
                ", salary=" + salary +
                ", currentBalance=" + currentBalance +
                ", expenses=" + expenses +
                '}';
    }
}
