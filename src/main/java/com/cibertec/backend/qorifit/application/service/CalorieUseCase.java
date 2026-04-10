package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.IngredientEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.MealLogEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.IngredientRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.MealLogRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.RecipeRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.LogCaloriesRequest;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.CalorieSummaryResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.MealLogEntryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalorieUseCase {

    private final MealLogRepoImpl mealLogRepoImpl;
    private final IngredientRepoImpl ingredientRepoImpl;
    private final RecipeRepoImpl recipeRepoImpl;
    private final UserRepoImpl userRepoImpl;
    private final NutritionCalculatorService nutritionCalculator;

    public void logMeal(Long userId, LogCaloriesRequest request) {

        UserEntity user = userRepoImpl.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Log each ingredient entry
        for (LogCaloriesRequest.IngredientLogItem item : request.ingredients()) {

            IngredientEntity ingredient = ingredientRepoImpl.findById(item.ingredientId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found: " + item.ingredientId()));

            BigDecimal calories = nutritionCalculator
                    .calculateFromIngredient(ingredient, item.quantityGrams());

            MealLogEntity log = new MealLogEntity();
            log.setUser(user);
            log.setIngredient(ingredient);
            log.setMealType(request.mealType());
            log.setQuantityGrams(item.quantityGrams());
            log.setCalories(calories);
            log.setDate(request.date());

            mealLogRepoImpl.save(log);
        }

        // Log each recipe entry
        for (Long recipeId : request.recipeIds()) {

            RecipeEntity recipe = recipeRepoImpl.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));

            BigDecimal calories = nutritionCalculator
                    .calculateFromRecipe(recipe, request.quantityGrams());

            MealLogEntity log = new MealLogEntity();
            log.setUser(user);
            log.setRecipe(recipe);
            log.setMealType(request.mealType());
            log.setQuantityGrams(request.quantityGrams());
            log.setCalories(calories);
            log.setDate(request.date());

            mealLogRepoImpl.save(log);
        }
    }

    public CalorieSummaryResponse getSummary(Long userId, LocalDate date) {

        List<MealLogEntity> logs = mealLogRepoImpl.findByUserIdAndDate(userId, date);

        BigDecimal total = logs.stream()
                .map(MealLogEntity::getCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<MealLogEntryResponse> entries = logs.stream()
                .map(log -> new MealLogEntryResponse(
                        log.getId(),
                        log.getIngredient() != null ? "INGREDIENT" : "RECIPE",
                        log.getIngredient() != null
                                ? log.getIngredient().getName()
                                : log.getRecipe().getName(),
                        log.getMealType(),
                        log.getQuantityGrams(),
                        log.getCalories(),
                        log.getLoggedAt()
                ))
                .toList();

        return new CalorieSummaryResponse(date, total, logs.size(), entries);
    }
}