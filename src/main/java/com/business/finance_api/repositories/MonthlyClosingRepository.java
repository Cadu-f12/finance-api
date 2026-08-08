package com.business.finance_api.repositories;

import com.business.finance_api.entities.MonthlyClosingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyClosingRepository extends JpaRepository<MonthlyClosingEntity, Long> {
    public boolean existsByYearAndMonth(Integer year, Integer month);

    public MonthlyClosingEntity findByYearAndMonth(Integer year, Integer month);
}
