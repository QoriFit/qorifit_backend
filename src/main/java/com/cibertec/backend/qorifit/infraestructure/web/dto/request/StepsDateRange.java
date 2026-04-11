package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record StepsDateRange(
        @NotNull
        LocalDate startDate,
        LocalDate endDate
) {
}
