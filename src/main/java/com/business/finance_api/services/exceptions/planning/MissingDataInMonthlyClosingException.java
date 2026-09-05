package com.business.finance_api.services.exceptions.planning;

public class MissingDataInMonthlyClosingException extends RuntimeException {
    public MissingDataInMonthlyClosingException(String message) {
        super(message);
    }
}
