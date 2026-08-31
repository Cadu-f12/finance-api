package com.business.finance_api.dto.planning;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record InvestmentRequest(
        @NotEmpty(message = "At least one investment allocation must be provided.")
        List<AllocationRequest> allocations
) {
    @Override
    public String toString() {
        return "InvestmentRequest{" +
                "allocations=" + allocations +
                '}';
    }
}
