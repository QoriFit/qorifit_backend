package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {

    @Query("""
    SELECT i FROM IngredientEntity i
    WHERE (CAST(:name AS string) IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
    AND (CAST(:category AS string) IS NULL OR LOWER(CAST(i.category AS string)) = LOWER(CAST(:category AS string)))
    ORDER BY
        CASE WHEN :order = 'desc' THEN i.name END DESC,
        CASE WHEN :order = 'asc'  THEN i.name END ASC,
        i.name ASC
""")
    List<IngredientEntity> findAllFiltered(
            @Param("name")     String name,
            @Param("category") String category,
            @Param("order")    String order
    );
}