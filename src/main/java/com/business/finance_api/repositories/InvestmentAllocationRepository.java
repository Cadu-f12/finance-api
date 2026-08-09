package com.business.finance_api.repositories;

import com.business.finance_api.entities.InvestmentAllocationEntity;
import com.business.finance_api.entities.MonthlyClosingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentAllocationRepository extends JpaRepository<InvestmentAllocationEntity, Long> {
    public boolean existsByMonthlyClosingAndModality(MonthlyClosingEntity monthlyClosingEntity, String name);
}
