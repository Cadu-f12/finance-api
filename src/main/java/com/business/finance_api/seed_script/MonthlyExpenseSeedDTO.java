package com.business.finance_api.seed_script;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthlyExpenseSeedDTO(
        BigDecimal amount,
        LocalDate monthlyClosing,
        String expenseCategory
) {}