package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RecipeRepoImpl {

    private final RecipeRepository repository;

    public Optional<RecipeEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<RecipeEntity> findAll() {
        return repository.findAll();
    }

    public List<RecipeEntity> findAllActive() {
        return repository.findAllActive();
    }

    public List<RecipeEntity> findAllFiltered(Long countryId, String name,
                                              Boolean popular, Boolean sortByPopularity) {
        return repository.findAllFiltered(countryId, name, popular, sortByPopularity);
    }

    public RecipeEntity save(RecipeEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
