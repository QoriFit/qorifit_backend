package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

}
