package com.business.finance_api.services.calculators;

import java.math.BigDecimal;

public class DistributionCalculator {
    private BigDecimal netBalance = BigDecimal.ZERO;

    public DistributionCalculator(BigDecimal netBalance) {
        this.netBalance = netBalance;
    }

    public BigDecimal calculate(BigDecimal percentage) {
        return netBalance.multiply(percentage);
    }
}
