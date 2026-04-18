package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import java.math.BigDecimal;

public record IngredientResponse(
        Long ingredientId,
        String name,
        BigDecimal caloriesPer100g,
        String category
) {}