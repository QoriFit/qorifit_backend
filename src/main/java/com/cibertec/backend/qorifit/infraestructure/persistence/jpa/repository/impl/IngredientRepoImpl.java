package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.IngredientEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IngredientRepoImpl {

    private final IngredientRepository repository;

    public Optional<IngredientEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<IngredientEntity> findAll() {
        return repository.findAll();
    }

    public IngredientEntity save(IngredientEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
