package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.MealLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MealLogRepository extends JpaRepository<MealLogEntity, Long> {

    Optional<MealLogEntity> findById(Long id);

    List<MealLogEntity> findAll();

    MealLogEntity save(MealLogEntity entity);

    void deleteById(Long id);
}
