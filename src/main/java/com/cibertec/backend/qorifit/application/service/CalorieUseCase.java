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

        // 1. Obtener la base calórica de la receta o del input manual
        BigDecimal baseCals = BigDecimal.ZERO;
        RecipeEntity recipeEntity = null;

        if (request.recipeId() != null) {
            recipeEntity = recipeRepoImpl.findById(request.recipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            // Si el usuario no mandó calorías custom, usamos las estimadas de la receta
            if (request.customCalories() != null) {
                baseCals = request.customCalories();
            } else if (recipeEntity.getEstimatedCalories() != null) {
                baseCals = BigDecimal.valueOf(recipeEntity.getEstimatedCalories());
            }
        } else {
            baseCals = (request.customCalories() != null) ? request.customCalories() : BigDecimal.ZERO;
        }

        // 2. Mapear ingredientes extra al modelo de dominio para el cálculo
        List<Ingredient> domainIngredients = request.ingredients().stream()
                .map(item -> {
                    IngredientEntity ent = ingredientRepoImpl.findById(item.id())
                            .orElseThrow(() -> new RuntimeException("Ingredient not found ID: " + item.id()));
                    // Convertimos el Long del request a BigDecimal para el dominio
                    return new Ingredient(ent.getCaloriesPer100g(), BigDecimal.valueOf(item.quantity()));
                }).toList();

        // 3. Crear objeto de dominio (Centraliza la lógica de suma total)
        CustomMeal domainMeal = new CustomMeal(
                request.mealName(), // Ahora usamos el nombre que viene del request
                request.mealType(),
                LocalDateTime.now(),
                baseCals,
                domainIngredients
        );

        // 4. Crear el Cabezal (MealLogEntity)
        MealLogEntity logEntity = new MealLogEntity();
        logEntity.setUser(user);
        logEntity.setRecipe(recipeEntity);
        logEntity.setMealType(domainMeal.getMealType().name());
        logEntity.setTotalCalories(domainMeal.getTotalCalories());
        logEntity.setDate(LocalDate.now());
        logEntity.setDisplayName(domainMeal.getCustomName());

        // 5. Crear los Detalles (MealLogDetailEntity)
        for (int i = 0; i < domainIngredients.size(); i++) {
            Ingredient domainIng = domainIngredients.get(i);
            Long ingredientId = request.ingredients().get(i).id();

            MealLogDetailEntity detail = new MealLogDetailEntity();
            detail.setIngredientId(ingredientId);
            detail.setQuantityGrams(domainIng.quantityGrams());
            detail.setCalories(domainIng.totalCalories());

            // Vinculación para el Cascade de JPA
            logEntity.getDetails().add(detail);
            detail.setMealLog(logEntity);
        }

        // 6. Persistencia atómica
        mealLogRepoImpl.save(logEntity);
    }

    public CalorieSummaryResponse getSummary(LocalDate date) {

        Long userId = contextHelper.extractUserId();

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