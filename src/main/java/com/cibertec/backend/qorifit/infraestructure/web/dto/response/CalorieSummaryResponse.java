package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CalorieSummaryResponse(
        LocalDate date,
        BigDecimal totalCalories,
        int totalEntries,
        List<MealLogEntryResponse> entries
) {}