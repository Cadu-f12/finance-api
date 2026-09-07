package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DistributionRequest(
        @PositiveOrZero(message = "Leisure percentage must be greater than or equal to 0.")
        @JsonProperty("leisure_percentage")
        BigDecimal leisurePercentage,

        @PositiveOrZero(message = "Leisure percentage must be greater than or equal to 0.")
        @JsonProperty("investment_percentage")
        BigDecimal investmentPercentage
) {
    @Override
    public String toString() {
        return "DistributionRequest{" +
                "leisurePercentage=" + leisurePercentage +
                ", investmentPercentage=" + investmentPercentage +
                '}';
    }
}
