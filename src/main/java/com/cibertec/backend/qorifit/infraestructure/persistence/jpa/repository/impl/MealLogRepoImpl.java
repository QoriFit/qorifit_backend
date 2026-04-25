package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.MealLogEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.MealLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MealLogRepoImpl {

    private final MealLogRepository repository;

    public Optional<MealLogEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<MealLogEntity> findAll() {
        return repository.findAll();
    }

    public MealLogEntity save(MealLogEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<MealLogEntity> findByUserIdAndDate(Long userId, LocalDate date) {
        return repository.findByUserIdAndDate(userId, date);
    }

    public List<MealLogEntity> findByUserIdAndDateSince(Long userId, LocalDate startDate){
        return repository.findByUserIdAndDateSince(userId, startDate);
    }
    public List<MealLogEntity> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate){
        return repository.findByUserIdAndDateRange(userId, startDate, endDate);

    }
}
