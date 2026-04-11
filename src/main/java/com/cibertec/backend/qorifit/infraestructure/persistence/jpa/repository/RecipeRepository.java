package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {


    @Query("SELECT r FROM RecipeEntity r WHERE r.isActive = true")
    List<RecipeEntity> findAllActive();


    @Modifying
    @Query("UPDATE RecipeEntity r SET r.isActive = false WHERE r.id = ?1")
    void deleteById(@NonNull Long id);
}
