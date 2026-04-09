package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.isActive = true")
    List<UserEntity> findAllActive();

    @Modifying
    @Query("UPDATE UserEntity u SET u.isActive = false WHERE u.id = ?1")
    void deleteById(@NonNull Long id);

    @Query("SELECT u FROM UserEntity u WHERE u.email=?1")
    Optional<UserEntity> findByEmail(String email);
}
