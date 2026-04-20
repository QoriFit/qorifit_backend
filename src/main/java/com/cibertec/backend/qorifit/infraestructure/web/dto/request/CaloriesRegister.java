package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import com.cibertec.backend.qorifit.domain.MealTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CaloriesRegister(

        Long recipeId,

        List<@Valid IngredientLogItem> ingredients,

        BigDecimal customCalories,

        @NotBlank
        String mealName,

        @NotNull
        MealTypeEnum mealType,

        LocalDate date

) {

}
