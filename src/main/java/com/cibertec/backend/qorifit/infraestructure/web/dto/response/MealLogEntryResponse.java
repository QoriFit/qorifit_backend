package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MealLogEntryResponse {

    private Long logId;
    private String source;
    private String name;
    private String mealType;
    private BigDecimal quantityGrams;
    private BigDecimal calories;
    private LocalDateTime loggedAt;
}