package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {

}
