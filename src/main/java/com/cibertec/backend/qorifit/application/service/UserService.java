package com.cibertec.backend.qorifit.application.service;


import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .build()
        ).toList();
    }
}
