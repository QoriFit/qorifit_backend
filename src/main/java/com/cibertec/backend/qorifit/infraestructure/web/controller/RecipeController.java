package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.RecipeUseCase;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.RecipeDetailResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.RecipeSummaryResponse;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeUseCase recipeUseCase;

    /**
     GET /recipes
     Lista las recetas activas con filtros opcionales.
     Si no se proporcionan filtros, utiliza por defecto el objetivo del usuario autenticado.
     Parámetros:
     countryId        — filtrar por país (ej. 1 para Perú)
     name             — búsqueda parcial por nombre, sin distinguir mayúsculas de minúsculas
     popular          — true = solo recetas con popularidad >= 4
     sortByPopularity  — true = ordenar por popularidad descendente
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<RecipeSummaryResponse>>> listRecipes(
            @RequestParam(required = false) Long countryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean popular,
            @RequestParam(required = false) Boolean sortByPopularity
    ) {
        Long userId = extractUserId();
        List<RecipeSummaryResponse> recipes = recipeUseCase.listRecipes(
                countryId, name, popular, sortByPopularity, userId
        );

        return ResponseEntity.ok(ApiResponse.<List<RecipeSummaryResponse>>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(recipes)
                .message("Recetas recibidas")
                .build());
    }


     /**GET /recipes/{id}
     Devuelve el detalle completo de la receta, incluyendo todos los ingredientes y sus calorías.*/
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecipeDetailResponse>> getDetail(
            @PathVariable Long id
    ) {
        RecipeDetailResponse detail = recipeUseCase.getDetail(id);

        return ResponseEntity.ok(ApiResponse.<RecipeDetailResponse>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(detail)
                .message("Detalle de receta recibido")
                .build());
    }

    private Long extractUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Long) auth.getPrincipal();
    }
}
