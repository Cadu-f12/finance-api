package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "expense_categories")
public class ExpenseCategoriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_fixed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isFixed;

    @OneToMany(mappedBy = "expenseCategory", fetch = FetchType.LAZY)
    private List<MonthlyExpenseEntity> expenseEntities;

    protected ExpenseCategoriesEntity() {}

    public ExpenseCategoriesEntity(Long id, String name, String description, Boolean isFixed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isFixed = isFixed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFixed() {
        return isFixed;
    }

    public void setFixed(Boolean fixed) {
        isFixed = fixed;
    }

    public List<MonthlyExpenseEntity> getExpenseEntities() {
        return expenseEntities;
    }

    public void setExpenseEntities(List<MonthlyExpenseEntity> expenseEntities) {
        this.expenseEntities = expenseEntities;
    }
}
