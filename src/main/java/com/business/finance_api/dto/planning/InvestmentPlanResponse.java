package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record InvestmentPlanResponse(
        @JsonProperty("investment_total")
        BigDecimal investmentTotal,

        List<AllocationResponse> allocations
) {
    @Override
    public String toString() {
        return "InvestmentPlanResponse{" +
                "investmentTotal=" + investmentTotal +
                ", allocations=" + allocations +
                '}';
    }
}
