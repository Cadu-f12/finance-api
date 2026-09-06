package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record InvestmentResponse(
        String message,

        @JsonProperty("monthly_closing_id")
        Long monthlyClosingId,

        @JsonProperty("reference_date")
        LocalDate referenceDate,

        @JsonProperty("month_plan_summary")
        MonthPlanResponse monthPlanSummary,

        @JsonProperty("investment_plan_summary")
        InvestmentPlanResponse investmentPlanSummary
) {
    @Override
    public String toString() {
        return "InvestmentResponse{" +
                "message='" + message + '\'' +
                ", monthlyClosingId=" + monthlyClosingId +
                ", referenceDate=" + referenceDate +
                ", monthPlanSummary=" + monthPlanSummary +
                ", investmentPlanSummary=" + investmentPlanSummary +
                '}';
    }
}
