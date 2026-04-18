package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.IngredientRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.IngredientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientUseCase {

    private final IngredientRepoImpl ingredientRepoImpl;

    public List<IngredientResponse> listIngredients(String name, String category, String order) {

        // Normalizar el orden: establecer "asc" por defecto si no se envía nada o si el valor no es válido.
        String normalizedOrder = (order != null && order.equalsIgnoreCase("desc"))
                ? "desc"
                : "asc";

        return ingredientRepoImpl
                .findAllFiltered(name, category, normalizedOrder)
                .stream()
                .map(i -> new IngredientResponse(
                        i.getId(),
                        i.getName(),
                        i.getCaloriesPer100g(),
                        i.getCategory()
                ))
                .toList();
    }
}