package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.ReminderEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReminderRepoImpl {

    private final ReminderRepository repository;

    public Optional<ReminderEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<ReminderEntity> findAll() {
        return repository.findAll();
    }

    public List<ReminderEntity> findAllActive() {
        return repository.findAllActive();
    }

    public ReminderEntity save(ReminderEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
