package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.StepEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StepRepoImpl {

    private final StepRepository repository;

    public Optional<StepEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<StepEntity> findAll() {
        return repository.findAll();
    }

    public StepEntity save(StepEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
