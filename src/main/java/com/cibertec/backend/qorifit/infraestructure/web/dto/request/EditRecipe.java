package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record EditRecipe(

        @NotNull
        Long recipeId,

        String recipeName,
        String description,
        String instructions,
        Long estimatedCalories,

        String imagePath


) {
}
