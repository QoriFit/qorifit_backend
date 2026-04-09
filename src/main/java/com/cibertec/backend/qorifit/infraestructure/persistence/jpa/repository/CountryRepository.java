package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Optional<CountryEntity> findById(Long id);

    List<CountryEntity> findAll();

    CountryEntity save(CountryEntity entity);

    void deleteById(Long id);
}
