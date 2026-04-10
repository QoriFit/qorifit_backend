package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.MealLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealLogRepository extends JpaRepository<MealLogEntity, Long> {

    Optional<MealLogEntity> findById(Long id);

    List<MealLogEntity> findAll();

    MealLogEntity save(MealLogEntity entity);

    void deleteById(Long id);

    @Query("SELECT ml FROM MealLogEntity ml WHERE ml.user.id = :userId AND ml.date = :date")
    List<MealLogEntity> findByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

}
