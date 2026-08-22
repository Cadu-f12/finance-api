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

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "current_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "leisure_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal leisurePercentage;

    @Column(name = "investment_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal investmentPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MonthlyClosingStatus status;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "monthlyClosing",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<MonthlyExpenseEntity> monthlyExpenses;

    @OneToMany(
            mappedBy = "monthlyClosing",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<InvestmentAllocationEntity> investmentAllocations;

    protected MonthlyClosingEntity() {}

    public MonthlyClosingEntity(
            BigDecimal salary,
            BigDecimal currentBalance,
            LocalDate referenceDate
    ) {
        this.salary = salary;
        this.currentBalance = currentBalance;
        this.referenceDate = referenceDate;

        this.leisurePercentage = BigDecimal.ZERO;
        this.investmentPercentage = BigDecimal.ZERO;

        this.status = MonthlyClosingStatus.PLANNING;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getLeisurePercentage() {
        return leisurePercentage;
    }

    public BigDecimal getInvestmentPercentage() {
        return investmentPercentage;
    }

    public MonthlyClosingStatus getStatus() {
        return status;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<MonthlyExpenseEntity> getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public List<InvestmentAllocationEntity> getInvestmentAllocations() {
        return investmentAllocations;
    }

    public void setLeisurePercentage(BigDecimal leisurePercentage) {
        this.leisurePercentage = leisurePercentage;
    }

    public void setInvestmentPercentage(BigDecimal investmentPercentage) {
        this.investmentPercentage = investmentPercentage;
    }

    public void open() {
        if (this.status != MonthlyClosingStatus.PLANNING) {
            throw new IllegalStateException(
                    "Only a monthly closing in planning can be opened."
            );
        }

        this.status = MonthlyClosingStatus.OPEN;
    }

    public void close() {
        if (this.status != MonthlyClosingStatus.OPEN) {
            throw new IllegalStateException(
                    "Only an open monthly closing can be closed."
            );
        }

        this.status = MonthlyClosingStatus.CLOSED;
        this.closedAt = LocalDateTime.now();
    }
}