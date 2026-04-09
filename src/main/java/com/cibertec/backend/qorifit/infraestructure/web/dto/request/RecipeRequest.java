package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record RecipeRequest(

        @NotNull
        Long id,

        @NotNull
        Long quantity
) {
}
