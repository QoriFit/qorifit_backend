package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealLogEntry {

    private Long logId;
    private String displayName;
    private BigDecimal totalCalories;

}