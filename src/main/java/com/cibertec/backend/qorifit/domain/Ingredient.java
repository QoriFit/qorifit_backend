package com.cibertec.backend.qorifit.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record Ingredient(
        BigDecimal caloriesPer100g,
        BigDecimal quantityGrams,
        BigDecimal totalCalories
) {

    public Ingredient {
        if (caloriesPer100g == null || quantityGrams == null) {
            throw new IllegalArgumentException("Valores nutricionales no pueden ser nulos");
        }

        totalCalories = caloriesPer100g
                .multiply(quantityGrams)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal sumTotal(List<Ingredient> logs) {
        return logs.stream()
                .map(Ingredient::totalCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}