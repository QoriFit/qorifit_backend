package com.cibertec.backend.qorifit.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public final class CustomMeal {

    private final String customName;
    private final MealTypeEnum mealType;
    private final LocalDateTime consumedAt;

    // Lo que viene de la receta (ya sea el total original o ajustado por el usuario)
    private final BigDecimal baseCalories;

    private final List<Ingredient> extraIngredients;

    private final BigDecimal totalCalories;

    public CustomMeal(
            String customName,
            MealTypeEnum mealType,
            LocalDateTime consumedAt,
            BigDecimal baseCalories,
            List<Ingredient> extraIngredients
    ) {
        this.customName = Objects.requireNonNull(customName, "El nombre es obligatorio");
        this.mealType = Objects.requireNonNull(mealType, "El tipo de comida es obligatorio");
        this.consumedAt = (consumedAt != null) ? consumedAt : LocalDateTime.now();

        this.baseCalories = (baseCalories != null) ? baseCalories : BigDecimal.ZERO;

        this.extraIngredients = (extraIngredients != null) ? List.copyOf(extraIngredients) : List.of();

        this.totalCalories = calculateFinalTotal();
    }

    private BigDecimal calculateFinalTotal() {
        BigDecimal extrasSum = extraIngredients.stream()
                .map(Ingredient::getTotalCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return baseCalories.add(extrasSum).setScale(2, RoundingMode.HALF_UP);
    }

    public String getCustomName() { return customName; }
    public MealTypeEnum getMealType() { return mealType; }
    public LocalDateTime getConsumedAt() { return consumedAt; }
    public BigDecimal getBaseCalories() { return baseCalories; }
    public List<Ingredient> getExtraIngredients() { return extraIngredients; }
    public BigDecimal getTotalCalories() { return totalCalories; }

    @Override
    public String toString() {
        return String.format("CustomMeal{name='%s', type=%s, total=%s kcal}",
                customName, mealType, totalCalories);
    }
}