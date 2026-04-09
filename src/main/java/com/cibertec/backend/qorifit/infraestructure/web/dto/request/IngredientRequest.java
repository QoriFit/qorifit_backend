package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record IngredientRequest(

        @NotNull
        Long id,

        @NotNull
        Long quantity
) {
}
