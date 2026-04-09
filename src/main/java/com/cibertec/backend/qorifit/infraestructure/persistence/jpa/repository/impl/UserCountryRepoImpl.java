package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserCountryEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.UserCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCountryRepoImpl {

    private final UserCountryRepository repository;

    public Optional<UserCountryEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<UserCountryEntity> findAll() {
        return repository.findAll();
    }

    public List<UserCountryEntity> findAllActive() {
        return repository.findAllActive();
    }

    public UserCountryEntity save(UserCountryEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
