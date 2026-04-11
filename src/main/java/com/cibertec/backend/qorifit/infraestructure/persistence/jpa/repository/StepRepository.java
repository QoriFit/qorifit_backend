package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
