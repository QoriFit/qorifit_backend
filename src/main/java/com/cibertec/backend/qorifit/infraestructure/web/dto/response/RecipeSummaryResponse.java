package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

public record RecipeSummaryResponse(
        Long recipeId,
        String name,
        String description,
        String instructions,
        String countryName,
        String imagePath,
        Integer estimatedCalories,
        Integer popularity
) {}