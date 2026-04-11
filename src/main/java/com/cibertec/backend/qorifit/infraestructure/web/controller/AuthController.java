package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.AuthUseCase;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.auth.LoginRequest;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.auth.RegisterRequest;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.LoginResponse;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @RequestBody @Valid LoginRequest request
    ){

        LoginResponse response = authUseCase.login(request.email(), request.password());

        return ResponseEntity.ok(ApiResponse.<LoginResponse>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(response)
                .message("Login successful")
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(
            @RequestBody @Valid RegisterRequest request
    ){
        authUseCase.register(
                request.username(),
                request.email(),
                request.password(),
                request.age(),
                request.weight(),
                request.height(),
                request.goal());

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(InternalCodes.SUCCESS.getCode())
                //.data(response)
                .message("Registered successfully")
                .build());

    }




}
