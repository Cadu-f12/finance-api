package com.business.finance_api.repositories;

import com.business.finance_api.entities.ModalitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalitiesRepository extends JpaRepository<ModalitiesEntity, Long> {
    public boolean existsByName(String name);

    public ModalitiesEntity findByName(String name);
}
