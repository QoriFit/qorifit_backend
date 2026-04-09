package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {

    Optional<RecipeIngredientEntity> findById(Long id);

    List<RecipeIngredientEntity> findAll();

    RecipeIngredientEntity save(RecipeIngredientEntity entity);

    void deleteById(Long id);
}
