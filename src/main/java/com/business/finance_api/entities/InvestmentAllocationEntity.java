package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "investment_allocations")
public class InvestmentAllocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal percentage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monthly_closing_id")
    private MonthlyClosingEntity monthlyClosing;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modality_id")
    private ModalitiesEntity modality;

    protected InvestmentAllocationEntity() {}

    public InvestmentAllocationEntity(Long id, BigDecimal percentage, MonthlyClosingEntity monthlyClosing, ModalitiesEntity modality) {
        this.id = id;
        this.percentage = percentage;
        this.monthlyClosing = monthlyClosing;
        this.modality = modality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public MonthlyClosingEntity getMonthlyClosing() {
        return monthlyClosing;
    }

    public void setMonthlyClosing(MonthlyClosingEntity monthlyClosing) {
        this.monthlyClosing = monthlyClosing;
    }

    public ModalitiesEntity getModality() {
        return modality;
    }

    public void setModality(ModalitiesEntity modality) {
        this.modality = modality;
    }
}
