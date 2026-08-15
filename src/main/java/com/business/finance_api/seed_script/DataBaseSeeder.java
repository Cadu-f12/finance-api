package com.business.finance_api.seed_script;

import com.business.finance_api.entities.*;
import com.business.finance_api.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class DataBaseSeeder implements CommandLineRunner {

    private final MonthlyClosingRepository monthlyClosingRepository;
    private final MonthlyExpenseRepository monthlyExpenseRepository;
    private final InvestmentAllocationRepository investmentAllocationRepository;
    private final ModalitiesRepository modalitiesRepository;
    private final ExpenseCategoriesRepository expenseCategoriesRepository;
    private final JsonSeedLoader seedFromJson;

    public DataBaseSeeder(
        MonthlyClosingRepository monthlyClosingRepository,
        MonthlyExpenseRepository monthlyExpenseRepository,
        InvestmentAllocationRepository investmentAllocationRepository,
        ExpenseCategoriesRepository expenseCategoriesRepository,
        ModalitiesRepository modalitiesRepository,
        JsonSeedLoader seedFromJson
    ) {
        this.monthlyClosingRepository = monthlyClosingRepository;
        this.monthlyExpenseRepository = monthlyExpenseRepository;
        this.investmentAllocationRepository = investmentAllocationRepository;
        this.expenseCategoriesRepository = expenseCategoriesRepository;
        this.modalitiesRepository = modalitiesRepository;
        this.seedFromJson = seedFromJson;
    }

    @Override
    public void run(String... args) throws Exception {
        List<MonthlyClosingEntity> closingEntities = seedFromJson.getMonthlyClosingFromJSON("seed-data/monthly_closing.json");
        List<ExpenseCategoriesEntity> expenseCategoriesEntities = seedFromJson.getExpensesFromJSON("seed-data/expense_categories.json");
        List<ModalitiesEntity> modalitiesEntities = seedFromJson.getModalitiesFromJSON("seed-data/modalities.json");
        List<MonthlyExpenseSeedDTO> expenseEntities = seedFromJson.getMonthExpenseFromJSON("seed-data/monthly_expense.json");
        List<InvestmentAllocationSeedDTO> investmentEntities = seedFromJson.getInvestmentFromJSON("seed-data/investment_allocation.json");

        this.seedMonthlyClosing(closingEntities);
        this.seedExpenseCategories(expenseCategoriesEntities);
        this.seedModalities(modalitiesEntities);
        this.seedInvesmentAllocation(investmentEntities);
        this.seedExpenseClosing(expenseEntities);
    }

    private void seedMonthlyClosing(List<MonthlyClosingEntity> entities) {
        for (MonthlyClosingEntity entity : entities) {
            LocalDate date = entity.getReferenceDate();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (monthlyClosingRepository.existsByReferenceDate(date)) {
                System.err.printf("[SEED SKIPPED] Monthly closing ALREADY EXISTS for period %s.%n", formattedDate);
                continue;
            }

            monthlyClosingRepository.save(entity);

            System.out.printf("[SEED SUCCESS] Saved monthly closing for period %s.%n", formattedDate);
        }
    }

    private void seedModalities(List<ModalitiesEntity> entities) {
        for (ModalitiesEntity entity : entities) {
            String modalityName = entity.getName();

            if (this.modalitiesRepository.existsByName(modalityName)) {
                System.err.printf("[SEED SKIPPED] Modality '%s' already EXISTS!%n", modalityName);
                continue;
            }

            this.modalitiesRepository.save(entity);
            System.out.printf("[SEED SUCCESS] Saved modality '%s' with success.%n", modalityName);
        }
    }

    private void seedExpenseCategories(List<ExpenseCategoriesEntity> entities) {
        for (ExpenseCategoriesEntity entity : entities) {
            String expenseName = entity.getName();

            if (this.modalitiesRepository.existsByName(expenseName)) {
                System.err.printf("[SEED SKIPPED] Expense '%s' already EXISTS!%n", expenseName);
                continue;
            }

            this.expenseCategoriesRepository.save(entity);
            System.out.printf("[SEED SUCCESS] Saved expense '%s' with success.%n", expenseName);
        }
    }

    private void seedExpenseClosing(List<MonthlyExpenseSeedDTO> entities) {
        for (MonthlyExpenseSeedDTO entity : entities) {
            LocalDate referenceDate = entity.monthlyClosing();
            String expenseName = entity.expenseCategory();

            String formattedDate = referenceDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (!this.monthlyClosingRepository.existsByReferenceDate(referenceDate)) {
                System.err.printf("[SEED SKIPPED] Monthly closing NOT FOUND for period %s.%n", formattedDate);
                continue;
            }
            if (!this.expenseCategoriesRepository.existsByName(expenseName)) {
                System.err.printf("[SEED SKIPPED] Expense '%s' NOT FOUND!%n", expenseName);
                continue;
            }

            MonthlyClosingEntity monthlyClosingEntity = this.monthlyClosingRepository.findByReferenceDate(referenceDate);
            ExpenseCategoriesEntity expenseCategoryEntity = this.expenseCategoriesRepository.findByName(expenseName);

            MonthlyExpenseEntity monthlyExpense = new MonthlyExpenseEntity(null, entity.amount(), monthlyClosingEntity, expenseCategoryEntity);

            this.monthlyExpenseRepository.save(monthlyExpense);
        }
    }



    private void seedInvesmentAllocation(List<InvestmentAllocationSeedDTO> entities) {
        for (InvestmentAllocationSeedDTO entity : entities) {
            LocalDate referenceDate = entity.monthlyClosing();
            String modalityName = entity.modalityName();

            String formattedDate = referenceDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (!this.monthlyClosingRepository.existsByReferenceDate(referenceDate)) {
                System.err.printf("[SEED SKIPPED] Monthly closing NOT FOUND for period %s.%n", formattedDate);
                continue;
            }
            if (!this.modalitiesRepository.existsByName(modalityName)) {
                System.err.printf("[SEED SKIPPED] Modality '%s' NOT FOUND!%n", modalityName);
                continue;
            }

            MonthlyClosingEntity monthlyClosingEntity = this.monthlyClosingRepository.findByReferenceDate(referenceDate);
            ModalitiesEntity modalitiesEntity = this.modalitiesRepository.findByName(modalityName);

            InvestmentAllocationEntity investmentAllocation = new InvestmentAllocationEntity(null, entity.percentage(), monthlyClosingEntity, modalitiesEntity);

            this.investmentAllocationRepository.save(investmentAllocation);
        }
    }
}
