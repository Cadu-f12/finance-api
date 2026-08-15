package com.business.finance_api.repositories;

import com.business.finance_api.entities.MonthlyClosingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MonthlyClosingRepository extends JpaRepository<MonthlyClosingEntity, Long> {

    public boolean existsByReferenceDate(LocalDate date);

    public MonthlyClosingEntity findByReferenceDate(LocalDate date);

}
