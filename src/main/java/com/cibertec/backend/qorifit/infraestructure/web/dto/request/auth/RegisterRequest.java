package com.cibertec.backend.qorifit.infraestructure.web.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterRequest(

        String username,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        Long age,

        BigDecimal weight,

        BigDecimal height,

        String goal

) {
}
