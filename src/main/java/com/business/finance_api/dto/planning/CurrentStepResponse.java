package com.business.finance_api.dto.planning;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentStepResponse(
        @JsonProperty("current_step")
        PlanningSteps currentStep
) {
    @Override
    public String toString() {
        return "CurrentStepResponse{" +
                "currentStep=" + currentStep +
                '}';
    }
}
