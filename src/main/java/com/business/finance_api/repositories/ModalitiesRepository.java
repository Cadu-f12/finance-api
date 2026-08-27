package com.business.finance_api.repositories;

import com.business.finance_api.entities.ModalitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalitiesRepository extends JpaRepository<ModalitiesEntity, Long> {
    boolean existsByName(String name);

    ModalitiesEntity findByName(String name);
}
