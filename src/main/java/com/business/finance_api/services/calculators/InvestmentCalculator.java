package com.business.finance_api.services.calculators;

import java.math.BigDecimal;

public class InvestmentCalculator {
    private BigDecimal investmentAmount = BigDecimal.ZERO;

    public InvestmentCalculator(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public BigDecimal calculate(BigDecimal percentage) {
        return investmentAmount.multiply(percentage);
    }
}
