package com.cibertec.backend.qorifit.infraestructure.web.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterRequest(

        String username,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        LocalDate birthDate,

        BigDecimal weight,

        BigDecimal height,

        String goal,

        @NotNull
        @Min(1000)
        Long stepsGoal,

        @NotNull
        @Min(500)
        BigDecimal maxCaloriesPerDay

) {
}
