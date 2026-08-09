package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "investment_allocation",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_monthly_closing_modality",
                        columnNames = {"monthly_closing_id", "modality"}
                )
        }
)
public class InvestmentAllocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monthly_closing_id")
    private MonthlyClosingEntity monthlyClosing;

    @Column(name = "modality", length = 100, nullable = false)
    private String modality;

    @Column(name = "percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal percentage;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    protected InvestmentAllocationEntity() {}

    public InvestmentAllocationEntity(
            MonthlyClosingEntity monthlyClosing,
            BigDecimal percentage,
            String modality
    ) {
        this.monthlyClosing = monthlyClosing;
        this.percentage = percentage;
        this.modality = modality;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthlyClosingEntity getMonthlyClosing() {
        return monthlyClosing;
    }

    public void setMonthlyClosing(MonthlyClosingEntity monthlyClosing) {
        this.monthlyClosing = monthlyClosing;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
