package com.cibertec.backend.qorifit.application.service;


import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.UserDto;
import com.cibertec.backend.qorifit.infraestructure.web.exception.BusinessException;
import com.cibertec.backend.qorifit.infraestructure.web.exception.ResourceNotFoundException;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepoImpl userRepo;

    public List<UserDto> getUsers(){

        List<UserEntity> users = userRepo.findAll();

        return users.stream().map(u -> UserDto.builder()
                .userId(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .imageUrl(u.getImageUrl())
                .status(u.getIsActive())
                .build()
        ).toList();
    }


    @Transactional
    public void updateUser(UserDto userDto) {

        UserEntity user = userRepo.findById(userDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userDto.getUsername() != null &&
                !Objects.equals(userDto.getUsername(), user.getUsername())) {
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getEmail() != null &&
                !Objects.equals(userDto.getEmail(), user.getEmail())) {

            userRepo.findByEmail(userDto.getEmail())
                    .filter(u -> !Objects.equals(u.getId(), user.getId()))
                    .ifPresent(u -> {
                        throw new BusinessException(
                                InternalCodes.DUPLICATED_EMAIL_ADDRESS,
                                "Email is already in use by another user"
                        );
                    });

            user.setEmail(userDto.getEmail());
        }

        if (userDto.getImageUrl() != null) {
            user.setImageUrl(userDto.getImageUrl());
        }

        if (userDto.getStatus() != null) {
            user.setIsActive(userDto.getStatus());
        }

        userRepo.save(user);
    }
}
