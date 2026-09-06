package com.business.finance_api.services.exceptions.planning;

public class InvalidListOfPercentagesException extends RuntimeException {
    public InvalidListOfPercentagesException(String message) {
        super(message);
    }
}
