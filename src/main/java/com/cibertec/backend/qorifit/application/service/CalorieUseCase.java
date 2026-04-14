package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.application.helper.ContextHelper;
import com.cibertec.backend.qorifit.domain.CustomMeal;
import com.cibertec.backend.qorifit.domain.Ingredient;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.*;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.IngredientRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.MealLogRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.RecipeRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.CaloriesRegister;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.CalorieSummaryResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.MealLogEntryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalorieUseCase {

    private final MealLogRepoImpl mealLogRepoImpl;
    private final IngredientRepoImpl ingredientRepoImpl;
    private final RecipeRepoImpl recipeRepoImpl;
    private final UserRepoImpl userRepoImpl;
    private final NutritionCalculatorService nutritionCalculator;
    private final ContextHelper contextHelper;

    @Transactional
    public void logMeal(@Valid CaloriesRegister request) {
        Long userId = contextHelper.extractUserId();
        UserEntity user = userRepoImpl.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal baseCals = BigDecimal.ZERO;
        RecipeEntity recipeEntity = null;

        if (request.recipeId() != null) {
            recipeEntity = recipeRepoImpl.findById(request.recipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            if (request.customCalories() != null) {
                baseCals = request.customCalories();
            } else if (recipeEntity.getEstimatedCalories() != null) {
                baseCals = BigDecimal.valueOf(recipeEntity.getEstimatedCalories());
            }
        } else {
            baseCals = (request.customCalories() != null) ? request.customCalories() : BigDecimal.ZERO;
        }

        List<Ingredient> domainIngredients = request.ingredients().stream()
                .map(item -> {
                    IngredientEntity ent = ingredientRepoImpl.findById(item.id())
                            .orElseThrow(() -> new RuntimeException("Ingredient not found ID: " + item.id()));
                    return new Ingredient(ent.getCaloriesPer100g(), BigDecimal.valueOf(item.quantity()));
                }).toList();

        CustomMeal domainMeal = new CustomMeal(
                request.mealName(),
                request.mealType(),
                LocalDateTime.now(),
                baseCals,
                domainIngredients
        );

        MealLogEntity logEntity = new MealLogEntity();
        logEntity.setUser(user);
        logEntity.setRecipe(recipeEntity);
        logEntity.setMealType(domainMeal.getMealType().name());
        logEntity.setTotalCalories(domainMeal.getTotalCalories());
        logEntity.setDate(LocalDate.now());
        logEntity.setDisplayName(domainMeal.getCustomName());

        for (int i = 0; i < domainIngredients.size(); i++) {
            Ingredient domainIng = domainIngredients.get(i);
            Long ingredientId = request.ingredients().get(i).id();

            MealLogDetailEntity detail = new MealLogDetailEntity();
            detail.setIngredientId(ingredientId);
            detail.setQuantityGrams(domainIng.getQuantityGrams());
            detail.setCalories(domainIng.getTotalCalories());

            logEntity.getDetails().add(detail);
            detail.setMealLog(logEntity);
        }

        mealLogRepoImpl.save(logEntity);
    }

    public CalorieSummaryResponse getSummary(LocalDate date) {

        Long userId = contextHelper.extractUserId();

        List<MealLogEntity> logs = mealLogRepoImpl.findByUserIdAndDate(userId, date);

        BigDecimal total = logs.stream()
                .map(MealLogEntity::getTotalCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<MealLogEntryResponse> entries = logs.stream()
                .map(log -> MealLogEntryResponse.builder()
                        .logId(log.getId())
                        .source(resolveSource(log))
                        .name(resolveName(log))
                        .mealType(log.getMealType())
                        .quantityGrams(calculateTotalGrams(log))
                        .calories(log.getTotalCalories())
                        .loggedAt(log.getLoggedAt())
                        .build()
                )
                .toList();

        return new CalorieSummaryResponse(date, total, logs.size(), entries);
    }

    private String resolveName(MealLogEntity log) {
        if (log.getDisplayName() != null) {
            return log.getDisplayName();
        }
        if (log.getRecipe() != null) {
            return log.getRecipe().getName();
        }
        return "Custom";
    }

    private String resolveSource(MealLogEntity log) {
        if (log.getRecipe() != null) {
            return "RECIPE";
        }
        return "INGREDIENT";
    }

    private BigDecimal calculateTotalGrams(MealLogEntity log) {
        return log.getDetails().stream()
                .map(MealLogDetailEntity::getQuantityGrams)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}