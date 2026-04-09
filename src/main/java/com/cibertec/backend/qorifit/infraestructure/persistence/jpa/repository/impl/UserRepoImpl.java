package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepoImpl {
    
    private final UserRepository repository;

    public Optional<UserEntity> findById(Long id) {
        return repository.findById(id);
    }

    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public List<UserEntity> findAllActive() {
        return repository.findAllActive();
    }

    public UserEntity save(UserEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
