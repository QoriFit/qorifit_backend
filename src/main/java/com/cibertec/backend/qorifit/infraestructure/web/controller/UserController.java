package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.UserService;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.UserDto;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public ResponseEntity<ApiResponse<?>> getUsers(){

        List<UserDto> response = userService.getUsers();

        return ResponseEntity.ok(ApiResponse.<List<UserDto>>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(response)
                .message("Obtained successfully")
                .build());
    }
}
