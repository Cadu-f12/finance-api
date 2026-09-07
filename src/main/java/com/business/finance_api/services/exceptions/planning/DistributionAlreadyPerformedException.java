package com.business.finance_api.services.exceptions.planning;

public class DistributionAlreadyPerformedException extends RuntimeException {
    public DistributionAlreadyPerformedException(String message) {
        super(message);
    }
}
