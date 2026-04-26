package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRecipe(

        @NotBlank
        String recipeName,

        @NotBlank
        String description,
        String instructions,
        String imageUrl,
        Long estimatedCalories
) {

}
