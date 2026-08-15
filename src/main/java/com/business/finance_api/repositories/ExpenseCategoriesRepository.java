package com.business.finance_api.repositories;

import com.business.finance_api.entities.ExpenseCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoriesRepository extends JpaRepository<ExpenseCategoriesEntity, Long> {

}
