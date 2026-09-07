package com.business.finance_api.services;

import com.business.finance_api.dto.planning.*;
import com.business.finance_api.entities.*;
import com.business.finance_api.repositories.*;
import com.business.finance_api.services.calculators.ExpenseSumCalculator;
import com.business.finance_api.services.exceptions.planning.*;
import com.business.finance_api.services.calculators.NetBalanceCalculator;
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

    private final InvestmentAllocationRepository investmentAllocationRepository;
    private final ModalitiesRepository modalitiesRepository;

    public PlanningService(
            MonthlyClosingRepository monthlyClosingRepository,
            MonthlyExpenseRepository monthlyExpenseRepository,
            ExpenseCategoriesRepository expenseCategoriesRepository,
            InvestmentAllocationRepository investmentAllocationRepository,
            ModalitiesRepository modalitiesRepository
    ) {
        this.monthlyClosingRepository = monthlyClosingRepository;
        this.monthlyExpenseRepository = monthlyExpenseRepository;
        this.expenseCategoriesRepository = expenseCategoriesRepository;
        this.investmentAllocationRepository = investmentAllocationRepository;
        this.modalitiesRepository = modalitiesRepository;
    }

    @Transactional
    public LiquidityResponse calculateLiquidity(LiquidityRequest request) {
        if (request.referenceDate().getDayOfMonth() != 1) {
            throw new IllegalArgumentException("The reference date must start on the 1st of the month.");
        }
        if (monthlyClosingRepository.existsByReferenceDate(request.referenceDate())) {
            throw new EntityExistsException(String.format("The date '%s' has already been created.", request.referenceDate()));
        }
        if (monthlyClosingRepository.existsByStatus(MonthlyClosingStatus.PLANNING)) {
            throw new EntityExistsException("Active planning is already in place.");
        }

        List<MonthlyExpenseEntity> expenseEntities = new ArrayList<>();
        List<BigDecimal> listOfExpenses = new ArrayList<>();

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
            listOfExpenses.add(expense.amount());
        }

        ExpenseSumCalculator expenseSumCalculator = new ExpenseSumCalculator(listOfExpenses);
        NetBalanceCalculator netBalanceCalculator = new NetBalanceCalculator(
            request.currentBalance(), expenseSumCalculator.calculate()
        );

        monthlyClosingRepository.save(closingEntity);
        monthlyExpenseRepository.saveAll(expenseEntities);

        return new LiquidityResponse(
                "Monthly planning started successfully.",
                closingEntity.getId(),
                closingEntity.getReferenceDate(),
                expenseSumCalculator.calculate(),
                netBalanceCalculator.calculate()
        );
    }

    @Transactional
    public DistributionResponse calculateDistribution(DistributionRequest request) {
        if (!this.monthlyClosingRepository.existsByStatus(MonthlyClosingStatus.PLANNING)) {
            throw new PlanningNotFoundException("The current monthly planning has not completed the liquidity step.");
        }
        BigDecimal sumValidation = request.leisurePercentage().add(request.investmentPercentage());
        if (sumValidation.compareTo(new BigDecimal("1")) != 0) {
            throw new IllegalArgumentException("The sum of leisure percentage and investment percentage is must equal 100%");
        }

        MonthlyClosingEntity monthlyClosing = monthlyClosingRepository.findByStatus(MonthlyClosingStatus.PLANNING);

        if (
            monthlyClosing.getLeisurePercentage().compareTo(BigDecimal.ZERO) != 0
            || monthlyClosing.getInvestmentPercentage().compareTo(BigDecimal.ZERO) != 0
        ) {
            throw new DistributionAlreadyPerformedException("The distribution calculation was already performed.");
        }

        monthlyClosing.setLeisurePercentage(request.leisurePercentage());
        monthlyClosing.setInvestmentPercentage(request.investmentPercentage());

        BigDecimal netBalance = request.netBalance();

        if (netBalance != null) {
            BigDecimal leisure = netBalance.multiply(request.leisurePercentage());
            BigDecimal investment = netBalance.multiply(request.investmentPercentage());

            DistributionValuesResponse responseValues = new DistributionValuesResponse(
                    leisure,
                    investment
            );

            return new DistributionResponse(
                    String.format("Monthly updated with %s to leisure and %s to investments.", request.leisurePercentage(), request.investmentPercentage()),
                    monthlyClosing.getId(),
                    monthlyClosing.getReferenceDate(),
                    responseValues
            );
        }

        BigDecimal liquidity = monthlyClosing.getCurrentBalance();
        List<MonthlyExpenseEntity> listOfExpenses = monthlyClosing.getMonthlyExpenses();

        for (MonthlyExpenseEntity expense : listOfExpenses) {
            BigDecimal amount = expense.getAmount();

            liquidity = liquidity.subtract(amount);
        }

        BigDecimal leisure = liquidity.multiply(request.leisurePercentage());
        BigDecimal investment = liquidity.multiply(request.investmentPercentage());

        DistributionValuesResponse responseValues = new DistributionValuesResponse(
                leisure,
                investment
        );

        return new DistributionResponse(
                String.format("Monthly updated with %s to leisure and %s to investments.", request.leisurePercentage(), request.investmentPercentage()),
                monthlyClosing.getId(),
                monthlyClosing.getReferenceDate(),
                responseValues
        );
    }

    @Transactional
    public InvestmentResponse calculateInvestment(InvestmentRequest request) {
        BigDecimal sumOfPercentages = BigDecimal.ZERO;
        for (AllocationRequest allocation : request.allocations()) {
            if (
                !this.modalitiesRepository.existsByName(allocation.modality())
            ) {
                throw new EntityNotFoundException(
                    String.format("Investment modality '%s' was not found.", allocation.modality())
                );
            }

            sumOfPercentages = sumOfPercentages.add(allocation.percentage());

            int modalitiesQuantity = 0;
            for (AllocationRequest modalityRequestCompare : request.allocations()) {
                if (allocation.modality().equals(modalityRequestCompare.modality())) {
                    modalitiesQuantity += 1;
                }
                if (modalitiesQuantity == 2) {
                    throw new DuplicateModalitiesException(
                            String.format("Investment modality '%s' was provided more than once.", allocation.modality())
                    );
                }
            }
        }
        if (sumOfPercentages.compareTo(new BigDecimal("1")) != 0) {
            throw new InvalidListOfPercentagesException("Investment allocations percentages must equal 100%.");
        }
        if (!this.monthlyClosingRepository.existsByStatus(MonthlyClosingStatus.PLANNING)) {
            throw new PlanningNotFoundException("No active monthly planning was found.");
        }

        MonthlyClosingEntity monthlyClosing = this.monthlyClosingRepository.findByStatus(MonthlyClosingStatus.PLANNING);

        if (
            monthlyClosing.getLeisurePercentage().compareTo(BigDecimal.ZERO) == 0
            || monthlyClosing.getInvestmentPercentage().compareTo(BigDecimal.ZERO) == 0
        ) {
            throw new MissingDataInMonthlyClosingException("The investment distribution cannot be performed because the monthly distribution has not been completed");
        }

        BigDecimal currentBalance = monthlyClosing.getCurrentBalance();
        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal netBalance = currentBalance;
        List<MonthlyExpenseEntity> listOfExpenses = monthlyClosing.getMonthlyExpenses();

        for (MonthlyExpenseEntity expense : listOfExpenses) {
            totalExpense = totalExpense.add(expense.getAmount());
            netBalance = netBalance.subtract(expense.getAmount());
        }

        BigDecimal leisureAmount = netBalance.multiply(monthlyClosing.getLeisurePercentage());
        BigDecimal investmentAmount = netBalance.multiply(monthlyClosing.getInvestmentPercentage());

        List<AllocationResponse> allocations = new ArrayList<>();

        for (AllocationRequest allocation : request.allocations()) {
            ModalitiesEntity modality = this.modalitiesRepository.findByName(allocation.modality());

            InvestmentAllocationEntity investmentAllocation = new InvestmentAllocationEntity(
                allocation.percentage(),
                monthlyClosing,
                modality
            );
            AllocationResponse allocationResponse = new AllocationResponse(
                allocation.modality(),
                allocation.percentage(),
                investmentAmount.multiply(allocation.percentage())
            );

            allocations.add(allocationResponse);
            this.investmentAllocationRepository.save(investmentAllocation);
        }

        MonthPlanResponse monthlyPlanSummary = new MonthPlanResponse(
            currentBalance,
            totalExpense,
            netBalance,
            leisureAmount
        );

        InvestmentPlanResponse investmentPlanSummary = new InvestmentPlanResponse(
            investmentAmount,
            allocations
        );

        monthlyClosing.setStatus(MonthlyClosingStatus.OPEN);
        return new InvestmentResponse(
            "Investments allocated successfully. Monthly planning is now OPEN!",
            monthlyClosing.getId(),
            monthlyClosing.getReferenceDate(),
            monthlyPlanSummary,
            investmentPlanSummary
        );
    }
}
