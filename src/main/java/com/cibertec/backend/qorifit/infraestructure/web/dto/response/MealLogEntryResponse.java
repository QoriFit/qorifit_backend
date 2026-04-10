package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MealLogEntryResponse(
        Long logId,
        String source,
        String name,
        String mealType,
        BigDecimal quantityGrams,
        BigDecimal calories,
        LocalDateTime loggedAt
) {}