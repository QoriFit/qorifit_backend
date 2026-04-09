package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StepRepository extends JpaRepository<StepEntity, Long> {

    Optional<StepEntity> findById(Long id);

    List<StepEntity> findAll();

    StepEntity save(StepEntity entity);

    void deleteById(Long id);
}
