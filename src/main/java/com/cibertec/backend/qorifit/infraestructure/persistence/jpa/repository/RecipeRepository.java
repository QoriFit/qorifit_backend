package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {


    @Query("SELECT r FROM RecipeEntity r WHERE r.isActive = true")
    List<RecipeEntity> findAllActive();


    //Query de busqueda por nombre, popularidad y país.
    @Query("""
        SELECT r FROM RecipeEntity r
        WHERE r.isActive = true
        AND (:countryId IS NULL OR r.country.id = :countryId)
        AND (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:popular IS NULL OR (:popular = true AND r.popularity >= 4)
                              OR (:popular = false AND r.popularity < 4))
        ORDER BY
            CASE WHEN :sortByPopularity = true THEN r.popularity END DESC,
            r.name ASC
    """)

    //Filtros aplicados
    List<RecipeEntity> findAllFiltered(
            @Param("countryId")       Long countryId,
            @Param("name")            String name,
            @Param("popular")         Boolean popular,
            @Param("sortByPopularity") Boolean sortByPopularity
    );
}
