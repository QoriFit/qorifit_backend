package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import java.util.List;

public record RecipeDetailResponse(
        Long recipeId,
        String name,
        String description,
        String countryName,
        String imagePath,
        Integer estimatedCalories,
        Integer popularity,
        List<RecipeIngredientResponse> ingredients
) {}
