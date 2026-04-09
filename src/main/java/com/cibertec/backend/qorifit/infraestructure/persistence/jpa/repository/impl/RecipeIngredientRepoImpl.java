package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeIngredientEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RecipeIngredientRepoImpl {

    private final RecipeIngredientRepository repository;

    public Optional<RecipeIngredientEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<RecipeIngredientEntity> findAll() {
        return repository.findAll();
    }

    public RecipeIngredientEntity save(RecipeIngredientEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
