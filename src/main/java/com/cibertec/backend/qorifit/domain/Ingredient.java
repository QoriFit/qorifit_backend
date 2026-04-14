package com.cibertec.backend.qorifit.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ingredient {

    private final BigDecimal caloriesPer100g;
    private final BigDecimal quantityGrams;
    private final BigDecimal totalCalories;

    public Ingredient(BigDecimal caloriesPer100g, BigDecimal quantityGrams) {
        if (caloriesPer100g == null || quantityGrams == null) {
            throw new IllegalArgumentException("Valores nutricionales no pueden ser nulos");
        }

        this.caloriesPer100g = caloriesPer100g;
        this.quantityGrams = quantityGrams;
        this.totalCalories = calculateTotalCalories();
    }

    private BigDecimal calculateTotalCalories() {
        return caloriesPer100g
                .multiply(quantityGrams)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public BigDecimal getQuantityGrams() {
        return quantityGrams;
    }

    public BigDecimal getTotalCalories() {
        return totalCalories;
    }
}