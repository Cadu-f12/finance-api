package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "monthly_expenses")
public class MonthlyExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monthly_closing_id")
    private MonthlyClosingEntity monthlyClosing;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private ExpenseCategoriesEntity expenseCategory;

    protected MonthlyExpenseEntity() {}

    public MonthlyExpenseEntity(Long id, BigDecimal amount, MonthlyClosingEntity monthlyClosing, ExpenseCategoriesEntity expenseCategory) {
        this.id = id;
        this.amount = amount;
        this.monthlyClosing = monthlyClosing;
        this.expenseCategory = expenseCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public MonthlyClosingEntity getMonthlyClosing() {
        return monthlyClosing;
    }

    public void setMonthlyClosing(MonthlyClosingEntity monthlyClosing) {
        this.monthlyClosing = monthlyClosing;
    }

    public ExpenseCategoriesEntity getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategoriesEntity expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
}

