package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserCountryEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserCountryRepository extends JpaRepository<UserCountryEntity, Long> {

    Optional<UserCountryEntity> findById(Long id);

    List<UserCountryEntity> findAll();

    @Query("SELECT uc FROM UserCountryEntity uc WHERE uc.isActive = true")
    List<UserCountryEntity> findAllActive();

    UserCountryEntity save(UserCountryEntity entity);

    @Modifying
    @Query("UPDATE UserCountryEntity uc SET uc.isActive = false WHERE uc.id = ?1")
    void deleteById(@NonNull Long id);
}
