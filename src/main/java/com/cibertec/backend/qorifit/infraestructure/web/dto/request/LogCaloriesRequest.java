package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record LogCaloriesRequest(

        @NotNull
        List<IngredientLogItem> ingredients,

        @NotNull
        List<Long> recipeIds,

        @NotBlank
        String mealType,

        @NotNull
        @DecimalMin("0.1")
        BigDecimal quantityGrams,

        @NotNull
        LocalDate date

) {

}