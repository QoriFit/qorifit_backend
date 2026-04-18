package com.cibertec.backend.qorifit.infraestructure.web.dto.response;


import java.math.BigDecimal;

public record RecipeIngredientResponse(
        Long ingredientId,
        String ingredientName,
        String category,
        BigDecimal quantityGrams,
        BigDecimal caloriesContributed
) {}
