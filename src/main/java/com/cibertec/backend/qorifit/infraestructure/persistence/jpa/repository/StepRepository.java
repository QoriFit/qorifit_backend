package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<StepEntity, Long> {


    @Query(value = """
        SELECT * FROM steps
        WHERE user_id = :userId 
        AND date BETWEEN :startDate AND :endDate
        ORDER BY date ASC
        """, nativeQuery = true)
    List<StepEntity> getStepsByUserIdAndDatesRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query(value = "SELECT * FROM steps WHERE user_id = :userId AND date = :date LIMIT 1",
           nativeQuery = true)
    java.util.Optional<StepEntity> findByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

    // 2. Desde una fecha específica hasta hoy
    @Query(value = """
        SELECT * FROM steps 
        WHERE user_id = :userId 
        AND date >= :startDate
        ORDER BY date DESC
        """, nativeQuery = true)
    List<StepEntity> getStepsByUserIdAndDateSince(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate
    );
}
