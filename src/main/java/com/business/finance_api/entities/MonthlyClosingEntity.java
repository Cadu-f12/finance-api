package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "monthly_closing")
public class MonthlyClosingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_date", unique = true, nullable = false)
    private LocalDate referenceDate;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "current_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "leisure_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal leisurePercentage;

    @Column(name = "investment_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal investmentPercentage;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "monthlyClosing", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MonthlyExpenseEntity> monthlyExpense;

    @OneToMany(mappedBy = "monthlyClosing", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<InvestmentAllocationEntity> investmentAllocation;

    protected MonthlyClosingEntity() {}

    public MonthlyClosingEntity(
            BigDecimal investmentPercentage,
            BigDecimal leisurePercentage,
            BigDecimal currentBalance,
            LocalDate referenceDate
    ) {
        this.investmentPercentage = investmentPercentage;
        this.leisurePercentage = leisurePercentage;
        this.currentBalance = currentBalance;
        this.referenceDate = referenceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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
}
