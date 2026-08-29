package com.business.finance_api.dto.planning;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DistributionValuesResponse(
        BigDecimal leisure,
        BigDecimal investment
) {
    @Override
    public String toString() {
        return "DistributionValues{" +
                "leisure=" + leisure +
                ", investment=" + investment +
                '}';
    }
}
