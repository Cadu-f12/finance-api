package com.business.finance_api.dto.planning;

import java.math.BigDecimal;

public record AllocationResponse(
        String modality,

        BigDecimal percentage,

        BigDecimal amount
) {
    @Override
    public String toString() {
        return "AllocationResponse{" +
                "modality='" + modality + '\'' +
                ", percentage=" + percentage +
                ", amount=" + amount +
                '}';
    }
}
