package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record MonthPlanResponse(
        @JsonProperty("current_balance")
        BigDecimal currentBalance,

        @JsonProperty("total_expense")
        BigDecimal totalExpense,

        @JsonProperty("net_balance")
        BigDecimal netBalance,

        BigDecimal leisure
) {
    @Override
    public String toString() {
        return "MonthPlanResponse{" +
                "currentBalance=" + currentBalance +
                ", netBalance=" + netBalance +
                ", leisure=" + leisure +
                '}';
    }
}
