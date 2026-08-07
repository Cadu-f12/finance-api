package com.business.finance_api.repositories;

import com.business.finance_api.entities.MonthlyExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyExpenseRepository extends JpaRepository<MonthlyExpenseEntity, Long> {

}
