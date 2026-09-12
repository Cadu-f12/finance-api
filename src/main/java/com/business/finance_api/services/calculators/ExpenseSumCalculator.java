package com.business.finance_api.services.calculators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExpenseSumCalculator {
    private List<BigDecimal> expenses = new ArrayList<>();

    public ExpenseSumCalculator(List<BigDecimal> expenses) {
        this.expenses = expenses;
    }

    public BigDecimal calculate() {
        BigDecimal summation = BigDecimal.ZERO;

        for (BigDecimal expense : expenses) {
            summation = summation.add(expense);
        }

        return summation;
    }
}
