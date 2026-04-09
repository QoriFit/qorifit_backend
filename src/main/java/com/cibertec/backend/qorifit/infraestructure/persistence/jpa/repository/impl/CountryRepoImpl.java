package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.CountryEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CountryRepoImpl {

    private final CountryRepository repository;

    public Optional<CountryEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<CountryEntity> findAll() {
        return repository.findAll();
    }

    public CountryEntity save(CountryEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
