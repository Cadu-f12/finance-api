package com.business.finance_api.repositories;

import com.business.finance_api.entities.MonthlyClosingEntity;
import com.business.finance_api.entities.MonthlyClosingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MonthlyClosingRepository extends JpaRepository<MonthlyClosingEntity, Long> {
    boolean existsByReferenceDate(LocalDate date);

    MonthlyClosingEntity findByReferenceDate(LocalDate date);

    boolean existsByStatus(MonthlyClosingStatus status);

    MonthlyClosingEntity findByStatus(MonthlyClosingStatus status);
}