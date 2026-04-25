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
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.IngredientLogItem;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.CalorieSummaryResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.MealLogEntry;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.MealLogEntryResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.MealSummaryByDate;
import com.cibertec.backend.qorifit.infraestructure.web.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalorieUseCase {

    private final MealLogRepoImpl mealLogRepoImpl;
    private final IngredientRepoImpl ingredientRepoImpl;
    private final RecipeRepoImpl recipeRepoImpl;
    private final UserRepoImpl userRepoImpl;
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

        List<IngredientLogItem> ingredients = request.ingredients() != null
                ? request.ingredients()
                : List.of();
        List<Ingredient> domainIngredients = ingredients.stream()
                .map(item -> {
                    IngredientEntity ent = ingredientRepoImpl.findById(item.ingredientId())
                            .orElseThrow(() -> new RuntimeException("Ingredient not found ID: " + item.ingredientId()));
                    return new Ingredient(ent.getCaloriesPer100g(), item.quantityGrams());
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
        logEntity.setDate(request.date() != null ? request.date() : LocalDate.now());
        logEntity.setDisplayName(domainMeal.getCustomName());

        for (int i = 0; i < domainIngredients.size(); i++) {
            Ingredient domainIng = domainIngredients.get(i);
            Long ingredientId = ingredients.get(i).ingredientId();

            MealLogDetailEntity detail = new MealLogDetailEntity();
            detail.setIngredientId(ingredientId);
            detail.setQuantityGrams(domainIng.getQuantityGrams());
            detail.setCalories(domainIng.getTotalCalories());

            logEntity.getDetails().add(detail);
            detail.setMealLog(logEntity);
        }

        mealLogRepoImpl.save(logEntity);
    }

    public List<MealSummaryByDate> getMealSummaryByDates(LocalDate startDate, LocalDate endDate) {

        Long userId = contextHelper.extractUserId();

        UserEntity user = userRepoImpl.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        List<MealLogEntity> mealLogs;

        if (endDate == null) {
            mealLogs = mealLogRepoImpl.findByUserIdAndDateSince(user.getId(), startDate);
        } else {
            mealLogs = mealLogRepoImpl.findByUserIdAndDateRange(user.getId(), startDate, endDate);
        }

        Map<LocalDate, List<MealLogEntity>> mealsByDateMap = mealLogs.stream()
                .collect(Collectors.groupingBy(MealLogEntity::getDate));

        LocalDate end = (endDate != null) ? endDate : LocalDate.now();

        List<MealSummaryByDate> mealSummaryByDates = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(end); date = date.plusDays(1)) {
            List<MealLogEntity> dayLogs = mealsByDateMap.getOrDefault(date, List.of());

            List<MealLogEntry> entries = dayLogs.stream()
                    .map(log -> MealLogEntry.builder()
                            .logId(log.getId())
                            .displayName(log.getDisplayName())
                            .totalCalories(log.getTotalCalories())
                            .build())
                    .toList();

            BigDecimal dailyTotal = dayLogs.stream()
                    .map(MealLogEntity::getTotalCalories)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            mealSummaryByDates.add(MealSummaryByDate.builder()
                    .date(date)
                    .meals(entries)
                    .totalCalories(dailyTotal)
                    .build());
        }

        return mealSummaryByDates;
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