package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.IngredientUseCase;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.IngredientResponse;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientUseCase ingredientUseCase;

    /**

     GET /ingredients
     Lista todos los ingredientes con filtros opcionales.

     Parámetros:
     name     — búsqueda por nombre parcial, insensible a mayúsculas (ej. "poll" coincide con "Pollo")
     category — coincidencia exacta de categoría, insensible a mayúsculas (ej. "PROTEÍNA")
     order    — dirección de ordenamiento por nombre: "asc" (por defecto) o "desc"
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<IngredientResponse>>> listIngredients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String order
    ) {
        List<IngredientResponse> ingredients =
                ingredientUseCase.listIngredients(name, category, order);

        return ResponseEntity.ok(ApiResponse.<List<IngredientResponse>>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(ingredients)
                .message("Ingredients retrieved")
                .build());
    }
}