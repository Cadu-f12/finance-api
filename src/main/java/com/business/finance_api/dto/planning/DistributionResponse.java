package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DistributionResponse(
        String message,

        @JsonProperty("monthly_closing_id")
        Long monthlyClosingId,

        @JsonProperty("reference_date")
        LocalDate referenceDate,

        DistributionValuesResponse values
) {
    @Override
    public String toString() {
        return "DistributionResponse{" +
                "message='" + message + '\'' +
                ", monthlyClosingId=" + monthlyClosingId +
                ", referenceDate=" + referenceDate +
                ", values=" + values +
                '}';
    }
}
