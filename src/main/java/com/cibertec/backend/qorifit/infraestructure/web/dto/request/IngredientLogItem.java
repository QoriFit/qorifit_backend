package com.cibertec.backend.qorifit.infraestructure.web.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IngredientLogItem(
        @NotNull Long ingredientId,
        @NotNull @DecimalMin("0.1") BigDecimal quantityGrams
) {}