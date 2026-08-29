package com.business.finance_api.services.exceptions.planning;

public class PlanningNotFoundException extends RuntimeException {
    public PlanningNotFoundException(String message) {
        super(message);
    }
}
