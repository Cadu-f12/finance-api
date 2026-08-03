package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_closing")
public class MonthlyClosingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "month", nullable = false, unique = true, columnDefinition = "INT CHECK (month >= 1 AND month <= 12)")
    private Integer month;

    @Column(name = "year", nullable = false, unique = true)
    private Integer year;

    @Column(name = "current_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "leisure_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal leisurePercentage;

    @Column(name = "investment_percentage", nullable = false, precision = 5, scale = 4)
    private BigDecimal investmentPercentage;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
