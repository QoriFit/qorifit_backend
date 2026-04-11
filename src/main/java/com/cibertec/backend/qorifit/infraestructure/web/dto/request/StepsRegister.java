package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record StepsRegister(

        @NotNull
        Long userId,

        @NotNull
        LocalDate date,

        @NotNull
        Long stepCount
) {
}
