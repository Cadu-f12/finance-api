package com.business.finance_api.dto.planning;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AllocationRequest(
        @NotBlank(message = "Investment modality must not be blank.")
        String modality,
        @Positive(message = "Investment percentage must be greater than or equal to 0.")
        @NotNull(message = "Investment percentage must not be blank.")
        BigDecimal percentage
) {
    @Override
    public String toString() {
        return "AllocationRequest{" +
                "modality='" + modality + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}