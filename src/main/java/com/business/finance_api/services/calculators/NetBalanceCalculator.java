package com.business.finance_api.services.calculators;

import java.math.BigDecimal;

public class NetBalanceCalculator {
    private final BigDecimal currentBalance;
    private final BigDecimal expenseSummation;

    public NetBalanceCalculator(
            BigDecimal currentBalance,
            BigDecimal expenseSummation
    ) {
        this.currentBalance = currentBalance;
        this.expenseSummation = expenseSummation;
    }

    public BigDecimal calculate() {
        return currentBalance.subtract(expenseSummation);
    }
}
