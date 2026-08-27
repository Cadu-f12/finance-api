package com.business.finance_api.services;

import com.business.finance_api.dto.planning.LiquidityExpenseRequest;
import com.business.finance_api.dto.planning.LiquidityRequest;
import com.business.finance_api.dto.planning.LiquidityResponse;
import com.business.finance_api.entities.ExpenseCategoriesEntity;
import com.business.finance_api.entities.MonthlyClosingEntity;
import com.business.finance_api.entities.MonthlyClosingStatus;
import com.business.finance_api.entities.MonthlyExpenseEntity;
import com.business.finance_api.repositories.ExpenseCategoriesRepository;
import com.business.finance_api.repositories.MonthlyClosingRepository;
import com.business.finance_api.repositories.MonthlyExpenseRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanningService {
    private final MonthlyClosingRepository monthlyClosingRepository;
    private final MonthlyExpenseRepository monthlyExpenseRepository;
    private final ExpenseCategoriesRepository expenseCategoriesRepository;


    public PlanningService(
            MonthlyClosingRepository monthlyClosingRepository,
            MonthlyExpenseRepository monthlyExpenseRepository,
            ExpenseCategoriesRepository expenseCategoriesRepository
    ) {
        this.monthlyClosingRepository = monthlyClosingRepository;
        this.monthlyExpenseRepository = monthlyExpenseRepository;
        this.expenseCategoriesRepository = expenseCategoriesRepository;
    }

    @Transactional
    public LiquidityResponse calculateLiquidity(LiquidityRequest request) {
        if (request.referenceDate().getDayOfMonth() != 1) {
            throw new IllegalArgumentException("The reference date must start on the 1st of the month.");
        }
        if (monthlyClosingRepository.existsByReferenceDate(request.referenceDate())) {
            throw new EntityExistsException(String.format("The date '%s' has already been finalized.", request.referenceDate()));
        }
        if (monthlyClosingRepository.existsByStatus(MonthlyClosingStatus.PLANNING)) {
            throw new EntityExistsException("Active planning is already in place.");
        }

        List<MonthlyExpenseEntity> expenseEntities = new ArrayList<>();
        BigDecimal netBalance = request.currentBalance();

        MonthlyClosingEntity closingEntity = new MonthlyClosingEntity(
                request.salary(),
                request.currentBalance(),
                request.referenceDate(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                MonthlyClosingStatus.PLANNING
        );

        for (LiquidityExpenseRequest expense : request.expenses()) {
            ExpenseCategoriesEntity categoryEntity = expenseCategoriesRepository.findByName(expense.name())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("The expense '%s' was not found.\n", expense.name())
                    ));

            MonthlyExpenseEntity expenseEntity = new MonthlyExpenseEntity(
                    expense.amount(),
                    closingEntity,
                    categoryEntity
            );

            expenseEntities.add(expenseEntity);

            netBalance = netBalance.subtract(expense.amount());
        }

        monthlyClosingRepository.save(closingEntity);
        monthlyExpenseRepository.saveAll(expenseEntities);

        return new LiquidityResponse(
                "Monthly planning started successfully.",
                closingEntity.getId(),
                closingEntity.getReferenceDate(),
                netBalance
        );
    }
}
