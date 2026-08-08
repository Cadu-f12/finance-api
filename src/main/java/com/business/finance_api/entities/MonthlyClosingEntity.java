package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
    name = "monthly_closing",
    uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_monthly_closing_month_year",
                columnNames = {"month", "year"}
        )
    }
)
public class MonthlyClosingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "monthly_closing", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MonthlyExpenseEntity> monthlyExpense;

    @OneToMany(mappedBy = "monthly_closing", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<InvestmentAllocationEntity> investmentAllocation;

    @Column(name = "month", nullable = false, columnDefinition = "INT CHECK (month >= 1 AND month <= 12)")
    private Integer month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "current_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "leisure_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal leisurePercentage;

    @Column(name = "investment_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal investmentPercentage;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    protected MonthlyClosingEntity() {}

    public MonthlyClosingEntity(
            BigDecimal investmentPercentage,
            BigDecimal leisurePercentage,
            BigDecimal currentBalance,
            Integer year,
            Integer month
    ) {
        this.investmentPercentage = investmentPercentage;
        this.leisurePercentage = leisurePercentage;
        this.currentBalance = currentBalance;
        this.year = year;
        this.month = month;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MonthlyExpenseEntity> getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(List<MonthlyExpenseEntity> monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public List<InvestmentAllocationEntity> getInvestmentAllocation() {
        return investmentAllocation;
    }

    public void setInvestmentAllocation(List<InvestmentAllocationEntity> investmentAllocation) {
        this.investmentAllocation = investmentAllocation;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getLeisurePercentage() {
        return leisurePercentage;
    }

    public void setLeisurePercentage(BigDecimal leisurePercentage) {
        this.leisurePercentage = leisurePercentage;
    }

    public BigDecimal getInvestmentPercentage() {
        return investmentPercentage;
    }

    public void setInvestmentPercentage(BigDecimal investmentPercentage) {
        this.investmentPercentage = investmentPercentage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
