package com.business.finance_api.seed_script;

import com.business.finance_api.entities.InvestmentAllocationEntity;
import com.business.finance_api.entities.MonthlyClosingEntity;
import com.business.finance_api.entities.MonthlyExpenseEntity;
import com.business.finance_api.repositories.InvestmentAllocationRepository;
import com.business.finance_api.repositories.MonthlyClosingRepository;
import com.business.finance_api.repositories.MonthlyExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DataBaseSeeder implements CommandLineRunner {

    private final MonthlyClosingRepository monthlyClosingRepository;
    private final MonthlyExpenseRepository monthlyExpenseRepository;
    private final InvestmentAllocationRepository investmentAllocationRepository;
    private final JsonSeedLoader seedFromJson;

    public DataBaseSeeder(
        MonthlyClosingRepository monthlyClosingRepository,
        MonthlyExpenseRepository monthlyExpenseRepository,
        InvestmentAllocationRepository investmentAllocationRepository,
        JsonSeedLoader seedFromJson
    ) {
        this.monthlyClosingRepository = monthlyClosingRepository;
        this.monthlyExpenseRepository = monthlyExpenseRepository;
        this.investmentAllocationRepository = investmentAllocationRepository;
        this.seedFromJson = seedFromJson;
    }

    @Override
    public void run(String... args) throws Exception {
        List<MonthlyClosingEntity> closingEntities = seedFromJson.getMonthlyClosingFromJSON("seed-data/monthly_closing.json");
        List<MonthlyExpenseEntity> expenseEntities = seedFromJson.getMonthExpenseFromJSON("seed-data/monthly_expense.json");
        List<InvestmentAllocationEntity> investmentEntities = seedFromJson.getInvestmentFromJSON("seed-data/investment_allocation.json");

        this.seedMonthlyClosing(closingEntities);
        this.seedExpenseClosing(expenseEntities);
        this.seedInvesmentAllocation(investmentEntities);
    }

    private void seedMonthlyClosing(List<MonthlyClosingEntity> entities) {
        for (MonthlyClosingEntity entity : entities) {
            Integer year = entity.getYear();
            Integer month = entity.getMonth();

            if (monthlyClosingRepository.existsByYearAndMonth(year, month)) {
                System.err.printf("[SEED SKIPPED] Monthly closing not found for period %02d/%d.%n", month, year);
                return;
            }

            monthlyClosingRepository.save(entity);

            System.out.printf("[SEED SUCCESS] Saved monthly closing for period %02d/%d.%n", month, year);
        }
    }

    private void seedExpenseClosing(List<MonthlyExpenseEntity> entities) {
        for (MonthlyExpenseEntity entity : entities) {
            Integer year = entity.getMonthlyClosing().getYear();
            Integer month = entity.getMonthlyClosing().getMonth();
            String name = entity.getName();

            if (!monthlyClosingRepository.existsByYearAndMonth(year, month)) {
                System.err.printf("[SEED SKIPPED] Monthly closing not found for period %02d/%d.%n", month, year);
                continue;
            }

            MonthlyClosingEntity closingParent = monthlyClosingRepository.findByYearAndMonth(year, month);

            if (monthlyExpenseRepository.existsByMonthlyClosingAndName(closingParent, name)) {
                System.err.printf("[SEED SKIPPED] Expense '%s' already exists.%n", name);
                continue;
            }

            entity.setMonthlyClosing(closingParent);

            monthlyExpenseRepository.save(entity);

            System.out.printf("[SEED SUCCESS] Saved expense '%s' for period %02d/%d.%n", name, month, year);
        }
    }

    private void seedInvesmentAllocation(List<InvestmentAllocationEntity> entities) {
        for (InvestmentAllocationEntity entity : entities) {
            Integer year = entity.getMonthlyClosing().getYear();
            Integer month = entity.getMonthlyClosing().getMonth();
            String modality = entity.getModality();

            if (!monthlyClosingRepository.existsByYearAndMonth(year, month)) {
                System.err.printf("[SEED SKIPPED] Monthly closing not found for period %02d/%d.%n", month, year);
                continue;
            }

            MonthlyClosingEntity closingParent = monthlyClosingRepository.findByYearAndMonth(year, month);

            if (investmentAllocationRepository.existsByMonthlyClosingAndModality(closingParent, modality)) {
                System.err.printf("[SEED SKIPPED] Investment of modality '%s' already exists.%n", modality);
                continue;
            }

            entity.setMonthlyClosing(closingParent);

            investmentAllocationRepository.save(entity);

            System.out.printf("[SEED SUCCESS] Saved investment '%s' for period %02d/%d.%n", modality, month, year);
        }
    }
}
