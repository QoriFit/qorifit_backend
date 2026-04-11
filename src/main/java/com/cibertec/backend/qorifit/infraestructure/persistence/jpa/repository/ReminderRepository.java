package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.ReminderEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<ReminderEntity, Long> {


    @Query("SELECT r FROM ReminderEntity r WHERE r.isActive = true")
    List<ReminderEntity> findAllActive();


    @Modifying
    @Query("UPDATE ReminderEntity r SET r.isActive = false WHERE r.id = ?1")
    void deleteById(@NonNull Long id);
}
