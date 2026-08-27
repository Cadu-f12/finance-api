package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LiquidityResponse(
        String message,

        @JsonProperty("monthly_closing_id")
        Long monthlyClosingId,

        @JsonProperty("reference_date")
        LocalDate referenceDate,

        @JsonProperty("net_balance")
        BigDecimal netBalance
) {

    @Override
    public String toString() {
        return "LiquidityResponse{" +
                "message='" + message + '\'' +
                ", monthlyClosingId=" + monthlyClosingId +
                ", referenceDate=" + referenceDate +
                ", netBalance=" + netBalance +
                '}';
    }
}
