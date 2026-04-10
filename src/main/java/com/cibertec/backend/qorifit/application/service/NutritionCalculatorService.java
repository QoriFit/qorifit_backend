package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.IngredientEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class NutritionCalculatorService {

    public BigDecimal calculateFromIngredient(IngredientEntity ingredient, BigDecimal quantityGrams) {
        return ingredient.getCaloriesPer100g()
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                .multiply(quantityGrams)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateFromRecipe(RecipeEntity recipe, BigDecimal quantityGrams) {
        return recipe.getRecipeIngredients().stream()
                .map(ri -> calculateFromIngredient(ri.getIngredient(), ri.getQuantityGrams()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}